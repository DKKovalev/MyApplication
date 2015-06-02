package com.example.psicho.myapplication;

import android.content.Context;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.telephony.CellInfo;
import android.telephony.CellInfoLte;
import android.telephony.TelephonyManager;
import android.telephony.cdma.CdmaCellLocation;
import android.telephony.gsm.GsmCellLocation;
import android.widget.TextView;

import java.util.List;

public class MainActivity extends ActionBarActivity {

    private CellInfoLte cellInfoLte;
    private TextView cellInfoTV;
    private TextView cellInfoLteTV;
    private CdmaCellLocation cellLocation;
    private int lat;
    private int lng;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupUI();
        TelephonyManager tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);

        switch (tm.getPhoneType()) {
            case 1:
                GsmCellLocation gsmCellLocation = (GsmCellLocation) tm.getCellLocation();
                cellInfoTV.setText("Lac: " + String.valueOf(gsmCellLocation.getLac())
                        + '\n' + "Cid: " + String.valueOf(gsmCellLocation.getCid())
                        + '\n' + "Phone type: " + String.valueOf(tm.getPhoneType()));
                break;
            case 2:
                cellLocation = (CdmaCellLocation) tm.getCellLocation();
                lat = cellLocation.getBaseStationLatitude();
                lng = cellLocation.getBaseStationLongitude();
                cellInfoTV.setText("Lat: " + lat
                        + '\n' + "Lng" + lng);
        }

        List<CellInfo> cellInfoList = tm.getAllCellInfo();
        for (CellInfo cellInfo : cellInfoList) {
            if (cellInfo instanceof CellInfoLte) {
                cellInfoLte = (CellInfoLte) cellInfo;
                cellInfoTV.setText(String.valueOf("Pci" + cellInfoLte.getCellIdentity().getPci()));
            }
        }

    }

    private void setupUI() {
        cellInfoTV = (TextView) findViewById(R.id.cell_info_TV);
        cellInfoLteTV = (TextView) findViewById(R.id.cell_info_lte_TV);

    }
}
