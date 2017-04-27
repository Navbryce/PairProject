
public class Weapon {
	public final static int automaticWeapon = 0;
	public final static int normalWeapon =1;
	
	private int fireType;
	private int magazineSize;
	private int bulletsLeftInClip;
	private int delay;
	private ParsedImageIcon weaponImage;
	private int reloadDelay;
	public Weapon(int clipSize, int fireTypeValue, int delayTime, int reloadTime, ParsedImageIcon image){
		magazineSize = clipSize;
		bulletsLeftInClip=clipSize;
		fireType = fireTypeValue;
		weaponImage=image;
		delay=delayTime;
		reloadDelay=reloadTime;
		
	}
	public ParsedImageIcon getImage(){
		return weaponImage;
	}
	public int getDelay(){
		return delay;
	}
	public void setDelay(int delayTime){
		delay=delayTime;
	}
	public int getMagazineSize(){
		return magazineSize;
	}
	public int getFireType(){
		return fireType;
	}
	public void setImage(ParsedImageIcon image){
		weaponImage=image;
	}
	public void setFireType(int fireTypeValue){
		fireType=fireTypeValue;
	}
	public void setMagazineSize(int clipSize){
		magazineSize=clipSize;
	}
	public int getBulletsLeftInClip(){
		return bulletsLeftInClip;
	}
	public void setBulletsLeftInClip(int bullets){
		bulletsLeftInClip=bullets;
	}
	public void reload(){
		bulletsLeftInClip=magazineSize;
	}
	public boolean needToReload(){
		boolean reload=false;
		if(bulletsLeftInClip<=0){
			reload=true;
		}
		return reload;
	}
	public void fire() throws Exception{
		if(bulletsLeftInClip<=0){
			throw new Exception();
		}else{
			bulletsLeftInClip--;
		}
	}
	public int getReloadDelay(){
		return reloadDelay;
	}
	public void setReloadDelay(int reloadTime){
		reloadDelay=reloadTime;
	}
	public Weapon replicate(){
		Weapon returnWeapon = new Weapon(magazineSize, fireType, delay, reloadDelay, weaponImage);
		returnWeapon.setBulletsLeftInClip(bulletsLeftInClip);
		
		return returnWeapon;
	}
}
