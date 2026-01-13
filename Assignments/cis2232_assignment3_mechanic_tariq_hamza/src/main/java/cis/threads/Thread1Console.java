package cis.threads;

import cis.util.CisUtility;
import cis.service.PartInvoiceService;

public class Thread1Console extends Thread {
    @Override
    public void run() {
        CisUtility cu = new CisUtility();
        cu.setIsGUI(false);

        PartInvoiceService service = new PartInvoiceService();
        service.ensureStorage(cu);
        service.runLoop(cu);
    }
}