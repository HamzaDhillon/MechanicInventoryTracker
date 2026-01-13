package cis.threads;

import cis.util.CisUtility;
import cis.service.PartInvoiceService;

public class Thread2GUI implements Runnable {
    @Override
    public void run() {
        CisUtility cu = new CisUtility();
        cu.setIsGUI(true);

        PartInvoiceService service = new PartInvoiceService();
        service.ensureStorage(cu);
        service.runLoop(cu);
    }
}