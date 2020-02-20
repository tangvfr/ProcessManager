package fr.tangv.web.util;

public class ByteMark {

	private int locMark;
	
	public ByteMark(byte[] data, byte[] mark) {
		for (int i = 0; i < data.length; i++) {
			if (data[i] == mark[0]) {
				for (int r = 1; r < mark.length && i+r < data.length; r++) {
					if (data[i+r] != mark[r])
						break;
					if (r == mark.length-1) {
						locMark = i+r;
						return;
					}
				}
			}
		}
		locMark = -1;
	}
	
	public int locMark() {
		return locMark;
	}
	
}
