class Float754{
	public static void main(String[] args){
		float var = (float) 0.8;
		int var754 = Float.floatToRawIntBits(var);
		System.out.println(var + " --> " + var754);
	}
}
