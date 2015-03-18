package kr.co.shineware.ds.trie.test;

public class MeasureResource {
	long	free;
	long	total;
	long	used;
	long	max;
	
	public MeasureResource() {
	}
	
	public MeasureResource(MeasureResource in) {
		this.free	= in.free;
		this.total	= in.total;
		this.used	= in.used;
		this.max	= in.max;
	}
	
	@Override
	public String toString() {
		StringBuilder	sb	= new StringBuilder();
		
		sb.append("max  : ");
		sb.append(Long.toString(max));
		sb.append("\r\n");
		sb.append("total: ");
		sb.append(Long.toString(total));
		sb.append("\r\n");
		sb.append("free : ");
		sb.append(Long.toString(free));
		
		return sb.toString();
	}

	public void update(boolean needGC, boolean print) {
		if(needGC) {
			System.out.println("GC trigered !!");
			System.gc();
		}

		long free = Runtime.getRuntime().freeMemory();
		long total = Runtime.getRuntime().totalMemory();
		long max = Runtime.getRuntime().maxMemory();
		long used = total - free;
		
		if(print) {
			System.out.println("Used Changed: " + (used - this.used));
		}
		
		this.free	= free;
		this.total	= total;
		this.max	= max;
		this.used	= used;
	}
}
