package com.hexabeast.riskisep.gameboard;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import com.hexabeast.riskisep.GameScreen;

public class Army {
	public ArrayList<Unite> soldiers = new ArrayList<Unite>();
	
	public int team = 0;
	public int newsoldiers = 10;
	
	public Army(int team)
	{
		this.team=team;
	}
	
	public Unite addSoldier(int type, int pays)
	{
		if(newsoldiers>=GameMaster.unitTypes[type].cout)
		{
			newsoldiers-=GameMaster.unitTypes[type].cout;
			return addSoldierForce(type, pays);
		}
		return null;
	}
	
	public Unite addSoldierForce(int type, int pays)
	{
		Unite s;
		if(type==0)s = new Soldat(GameScreen.master.curid, pays,team);
		else if(type==1)s = new Cheval(GameScreen.master.curid, pays,team);
		else s = new Cannon(GameScreen.master.curid, pays,team);
		soldiers.add(s);
		GameScreen.master.soldiersmap.put(String.valueOf(GameScreen.master.curid), s);
		AllPays.pays.get(pays).occupants.add(s);
		AllPays.pays.get(pays).team=team;
		GameScreen.master.curid++;
		return s;
	}
	
	public void removeSoldier(int id)
	{
		Unite s = GameScreen.master.soldiersmap.get(String.valueOf(id));
		Pays pays = AllPays.pays.get(s.pays); //Pays
		GameScreen.master.soldiersmap.remove(String.valueOf(s.id));
		s.dead=true;
		pays.occupants.remove(s);
		if(pays.occupants.size()==0)pays.team=-1;
	}
	
	public void transferSoldiers(int id, int paysdest)
	{
		if(GameScreen.master.soldiersmap.containsKey(String.valueOf(id)))
		{
			Unite s = GameScreen.master.soldiersmap.get(String.valueOf(id));
			s.mvtactuel-=1;
			Pays pays = AllPays.pays.get(s.pays); //Pays
			pays.occupants.remove(s);
			AllPays.pays.get(paysdest).occupants.add(s);
			s.pays = paysdest;
			s.randomizePos();
			AllPays.pays.get(paysdest).team=team;
			if(AllPays.pays.get(paysdest).occupants.size()==0)AllPays.pays.get(paysdest).team=-1;
		}
	}
	
	public void update()
	{
		Collections.sort(soldiers, new Comparator<Unite>() {
	        @Override
	        public int compare(Unite o1, Unite o2) {
	        	int posy =(int) ((o2.y-o1.y)*1000000);
	            return posy;
	        }
		});
		for(int i=0;i<soldiers.size();i++)
		{
			if(!soldiers.get(i).dead)soldiers.get(i).update();
		}
		for(int i=soldiers.size()-1;i>-1;i--)
		{
			if(soldiers.get(i).dead)soldiers.get(i).update();
			if(soldiers.get(i).transparency<=0)soldiers.remove(i);
		}
		
	}
	
	public ArrayList<Pays> getCountries()
	{
		ArrayList<Pays> c = new ArrayList<Pays>();
		for(int i=0;i<AllPays.pays.size();i++)
		{
			if(AllPays.pays.get(i).team==team)c.add(AllPays.pays.get(i));
		}
		return c;
	}

}
