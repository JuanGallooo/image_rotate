package model;

public class HiloEsperaPC implements Runnable {

	private ServidorCentral sc;
	private boolean pcDone;

	public HiloEsperaPC(ServidorCentral sc) {
		this.sc = sc;
		pcDone = false;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
//		while (true) {
//			if (pcDone) {
//				sc.distribute();
//				break;
//			}
//		}
	}

	public void verify() {
		if (sc.getRotadores().size()< sc.getNumPC()) {
			System.out.println("Se conecto el rotador: "+ sc.getRotadores().size());
		} else {
			sc.distribute();
		}

	}

}
