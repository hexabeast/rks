package com.hexabeast.riskisep.ressources;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.hexabeast.riskisep.Main;
import com.hexabeast.riskisep.gameboard.GameMaster;

public class Shaders {
	public static ShaderProgram base;
	public static ShaderProgram outline;
	public static ShaderProgram color;
	public static ShaderProgram colorsoldiers;
	public static ShaderProgram wave;
	
	public static void loadShaders()
	{
		ShaderProgram.pedantic = false;
		
		base = new ShaderProgram(Gdx.files.internal("assets/"+"shaders/passthroughvertex.fragx"),Gdx.files.internal("assets/"+"shaders/passthrough.fragx"));
		outline = new ShaderProgram(Gdx.files.internal("assets/"+"shaders/passthroughvertex.fragx"),Gdx.files.internal("assets/"+"shaders/outline.fragx"));
		color = new ShaderProgram(Gdx.files.internal("assets/"+"shaders/passthroughvertex.fragx"),Gdx.files.internal("assets/"+"shaders/color.fragx"));
		colorsoldiers = new ShaderProgram(Gdx.files.internal("assets/"+"shaders/passthroughvertex.fragx"),Gdx.files.internal("assets/"+"shaders/colorsoldiers.fragx"));
		wave = new ShaderProgram(Gdx.files.internal("assets/"+"shaders/passthroughvertex.fragx"),Gdx.files.internal("assets/"+"shaders/wave.fragx"));
		
		if(!base.isCompiled())System.out.println("base : "+base.getLog());
		if(!outline.isCompiled())System.out.println("outline : "+outline.getLog());
		if(!color.isCompiled())System.out.println("color"+color.getLog());
		if(!colorsoldiers.isCompiled())System.out.println("colorsol"+colorsoldiers.getLog());
		if(!wave.isCompiled())System.out.println("wave"+wave.getLog());
		
		setDefaultShader();
	}
	
	public static void setDefaultShader()
	{
		Main.batch.setShader(Shaders.base);
	}
	
	public static void setOutlineShader()
	{
		Main.batch.setShader(Shaders.outline);
	}
	

	
	public static void setSoldierTeamShader(int team, float transparency)
	{
		Main.batch.setShader(Shaders.colorsoldiers);
		Shaders.colorsoldiers.setUniformf("transparent", transparency);
		if(team==-1)
		{
			Shaders.colorsoldiers.setUniformf("rouge", 0.5f);
			Shaders.colorsoldiers.setUniformf("vert", 0.5f);
			Shaders.colorsoldiers.setUniformf("bleu",  0.5f);
		}
		else
		{
			Shaders.colorsoldiers.setUniformf("rouge", GameMaster.teamcol[team].r);
			Shaders.colorsoldiers.setUniformf("vert", GameMaster.teamcol[team].g);
			Shaders.colorsoldiers.setUniformf("bleu", GameMaster.teamcol[team].b);
		}
	}
	
	public static void setPaysTeamShader(int team, float intensity)
	{
		Main.batch.setShader(Shaders.color);
		Shaders.color.setUniformf("intensity", intensity);
		if(team==-1)
		{
			Shaders.color.setUniformf("rouge", 0.5f);
			Shaders.color.setUniformf("vert", 0.5f);
			Shaders.color.setUniformf("bleu",  0.5f);
		}
		else
		{
			Shaders.color.setUniformf("rouge", GameMaster.teamcol[team].r);
			Shaders.color.setUniformf("vert", GameMaster.teamcol[team].g);
			Shaders.color.setUniformf("bleu", GameMaster.teamcol[team].b);
		}
	}
	
	public static void setWhiteTeam(float transparency)
	{
		Main.batch.setShader(Shaders.colorsoldiers);
		Shaders.colorsoldiers.setUniformf("transparent", transparency);
		Shaders.colorsoldiers.setUniformf("rouge", 0.8f);
		Shaders.colorsoldiers.setUniformf("vert", 0.8f);
		Shaders.colorsoldiers.setUniformf("bleu", 0.8f);
	}
	
	public static void setWaveShader()
	{
		
		Main.batch.setShader(Shaders.wave);
		wave.setUniformf("u_time",Main.time,Main.time);
	}
}
