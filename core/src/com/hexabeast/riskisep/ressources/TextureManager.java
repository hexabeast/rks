package com.hexabeast.riskisep.ressources;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.math.Rectangle;

public class TextureManager { 
	private static ArrayList<Texture> textures = new ArrayList<Texture>();
	public static Map<String, Texture> tex = new HashMap<String, Texture>();
	
	public static Texture img;
	
	public static BitmapFont font;
	public static BitmapFont fontButton;
	public static GlyphLayout fontlayout;
	
	public static ArrayList<Pixmap> unitePixmap = new ArrayList<Pixmap>();
	
	public static void loadOne(String name, String namefile)
	{
		Texture temp = new Texture(namefile);
		tex.put(name, temp);
		textures.add(temp);
	}
	
	public static Pixmap loadPixMap(String nom)
	{
		Texture texture = TextureManager.tex.get(nom);
		if (!texture.getTextureData().isPrepared()) {
            texture.getTextureData().prepare();
        }
		return texture.getTextureData().consumePixmap();
	}
	
	public static boolean isPixTouched(float x, float y, float w, float h, Texture textu, Pixmap pix, float xx, float yy)
	{
		Rectangle spriteBounds = new Rectangle(x,y,w,h);
	    if (spriteBounds.contains(xx,yy)) {
	        float factor = textu.getWidth()/w;

	        int spriteLocalX = (int) (xx - x);
	        
	        int spriteLocalY = (int) (yy -y);

	        int textureLocalX = (int) (spriteLocalX*factor);
	        int textureLocalY = (int) (spriteLocalY*factor);
	        
	        Color col = new Color(pix.getPixel(textureLocalX, textu.getHeight()-textureLocalY));
	        if(textu==tex.get("cannon"))
	        {
	        	System.out.println(col.r);
		        System.out.println(col.g);
		        System.out.println(col.a);
	        }
	        
	        if(col.a<0.5)return false;
	        return true;
	    }
	    return false;
	}
	
	public static void load()
	{
		loadOne("background","ocean.png");
		loadOne("soldat","unites/rondrsoldat3.png");
		loadOne("cannon","unites/rondcanon2.png");
		loadOne("cheval","unites/rondcheval2.png");
		loadOne("rond","rond.png");
		loadOne("rondr","rondr.png");
		loadOne("rondb","rondb.png");
		loadOne("panneau","panneaubois.png");
		loadOne("panneauv","panneauvert.png");
		loadOne("blank","blank.png");
		
		FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/cartoon.ttf"));
		FreeTypeFontParameter parameter = new FreeTypeFontParameter();
		parameter.size = 28;
		font = generator.generateFont(parameter);
		parameter.size = 35;
		fontButton = generator.generateFont(parameter);
		generator.dispose();
		
		fontlayout = new GlyphLayout();
		
		unitePixmap.add(loadPixMap("soldat"));
		unitePixmap.add(loadPixMap("cheval"));
		unitePixmap.add(loadPixMap("cannon"));
		
	}
	
	public static Texture getsoldiertex(int team)
	{
		if(team==0)return tex.get("soldierr");
		else return tex.get("soldierb");
	}
	
	public static void dispose()
	{
		for(int i=0; i<textures.size(); i++)
		{
			textures.get(i).dispose();
		}
	}
}
