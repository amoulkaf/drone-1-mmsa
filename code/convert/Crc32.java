import java.util.zip.Checksum;
import java.util.zip.CRC32;


class Crc32{
	public static void main(String[] args){
		String str = "ses";
		byte bytes[] = str.getBytes();
		Checksum checksum = new CRC32();
		checksum.update(bytes,0,bytes.length);
		long lngChecksum = checksum.getValue();
		String ret = Long.toHexString(lngChecksum);
		System.out.println(str + " --> " + ret);
	}
}
