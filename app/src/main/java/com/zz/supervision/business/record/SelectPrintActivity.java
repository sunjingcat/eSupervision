package com.zz.supervision.business.record;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.net.wifi.WpsInfo;
import android.net.wifi.p2p.WifiP2pConfig;
import android.net.wifi.p2p.WifiP2pDevice;
import android.net.wifi.p2p.WifiP2pDeviceList;
import android.net.wifi.p2p.WifiP2pInfo;
import android.net.wifi.p2p.WifiP2pManager;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.snackbar.Snackbar;
import com.zz.lib.commonlib.utils.ToolBarUtils;
import com.zz.lib.core.ui.mvp.BasePresenter;
import com.zz.supervision.App;
import com.zz.supervision.R;
import com.zz.supervision.base.MyBaseActivity;
import com.zz.supervision.business.record.adapter.PrinterAdapter;
import com.zz.supervision.print.AppController;
import com.zz.supervision.print.PeerListFragment;
import com.zz.supervision.print.db.DBAdapter;
import com.zz.supervision.print.model.DeviceDTO;
import com.zz.supervision.print.notification.NotificationToast;
import com.zz.supervision.print.transfer.DataHandler;
import com.zz.supervision.print.transfer.DataSender;
import com.zz.supervision.print.transfer.TransferConstants;
import com.zz.supervision.print.utils.ConnectionUtils;
import com.zz.supervision.print.utils.DialogUtils;
import com.zz.supervision.print.utils.Utility;
import com.zz.supervision.print.wifidirect.WiFiDirectBroadcastReceiver;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentTransaction;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SelectPrintActivity extends MyBaseActivity implements
        PeerListFragment.OnListFragmentInteractionListener,WifiP2pManager.PeerListListener, WifiP2pManager.ConnectionInfoListener{
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    public static final String FIRST_DEVICE_CONNECTED = "first_device_connected";
    public static final String KEY_FIRST_DEVICE_IP = "first_device_ip";

    private static final String WRITE_PERMISSION = Manifest.permission.WRITE_EXTERNAL_STORAGE;
    private static final int WRITE_PERM_REQ_CODE = 19;

    PeerListFragment deviceListFragment;
    View progressBarLocalDash;

    WifiP2pManager wifiP2pManager;
    WifiP2pManager.Channel wifip2pChannel;
    WiFiDirectBroadcastReceiver wiFiDirectBroadcastReceiver;
    private boolean isWifiP2pEnabled = false;

    private boolean isWDConnected = false;

    private App appController;

    @Override
    protected int getContentView() {
        return R.layout.activity_select_print;
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);

        initialize();
        findPeers();
    }
    private void initialize() {

        progressBarLocalDash = findViewById(R.id.progressBarLocalDash);

        String myIP = Utility.getWiFiIPAddress(SelectPrintActivity.this);
        Utility.saveString(SelectPrintActivity.this, TransferConstants.KEY_MY_IP, myIP);

        wifiP2pManager = (WifiP2pManager) getSystemService(WIFI_P2P_SERVICE);
        wifip2pChannel = wifiP2pManager.initialize(this, getMainLooper(), null);

        // Starting connection listener with default port for now
        appController = (App) getApplicationContext();
        appController.startConnectionListener(TransferConstants.INITIAL_DEFAULT_PORT);

        checkWritePermission();
    }
    @Override
    protected void initToolBar() {
        ToolBarUtils.getInstance().setNavigation(toolbar,1);
    }

    @Override
    public BasePresenter initPresenter() {
        return null;
    }
    /**
     * @param isWifiP2pEnabled the isWifiP2pEnabled to set
     */
    public void setIsWifiP2pEnabled(boolean isWifiP2pEnabled) {
        this.isWifiP2pEnabled = isWifiP2pEnabled;
    }

    @Override
    public void onPeersAvailable(WifiP2pDeviceList peerList) {
        ArrayList<DeviceDTO> deviceDTOs = new ArrayList<>();

        List<WifiP2pDevice> devices = (new ArrayList<>());
        devices.addAll(peerList.getDeviceList());
        for (WifiP2pDevice device : devices) {
            DeviceDTO deviceDTO = new DeviceDTO();
            deviceDTO.setIp(device.deviceAddress);
            deviceDTO.setPlayerName(device.deviceName);
            deviceDTO.setDeviceName(new String());
            deviceDTO.setOsVersion(new String());
            deviceDTO.setPort(-1);
            deviceDTOs.add(deviceDTO);
        }


        progressBarLocalDash.setVisibility(View.GONE);
        deviceListFragment = new PeerListFragment();
        Bundle args = new Bundle();
        args.putSerializable(PeerListFragment.ARG_DEVICE_LIST, deviceDTOs);
        deviceListFragment.setArguments(args);

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.deviceListHolder, deviceListFragment);
        ft.setTransition(FragmentTransaction.TRANSIT_NONE);
        ft.commit();
    }

    boolean isConnectionInfoSent = false;
    @Override
    public void onConnectionInfoAvailable(WifiP2pInfo wifiP2pInfo) {
        if (wifiP2pInfo.groupFormed && !wifiP2pInfo.isGroupOwner && !isConnectionInfoSent) {

            isWDConnected = true;

//            connListener.tearDown();
//            connListener = new ConnectionListener(LocalDashWiFiDirect.this, ConnectionUtils.getPort
//                    (LocalDashWiFiDirect.this));
//            connListener.start();
//            appController.stopConnectionListener();
//            appController.startConnectionListener(ConnectionUtils.getPort(LocalDashWiFiDirect.this));
            appController.restartConnectionListenerWith(ConnectionUtils.getPort(SelectPrintActivity.this));

            String groupOwnerAddress = wifiP2pInfo.groupOwnerAddress.getHostAddress();
            DataSender.sendCurrentDeviceDataWD(SelectPrintActivity.this, groupOwnerAddress, TransferConstants
                    .INITIAL_DEFAULT_PORT, true);
            isConnectionInfoSent = true;
        }
    }
    private void checkWritePermission() {
        boolean isGranted = Utility.checkPermission(WRITE_PERMISSION, this);
        if (!isGranted) {
            Utility.requestPermission(WRITE_PERMISSION, WRITE_PERM_REQ_CODE, this);
        }
    }
    private DeviceDTO selectedDevice;
    @Override
    public void onListFragmentInteraction(DeviceDTO deviceDTO) {
//        if (!isWDConnected) {
//            WifiP2pConfig config = new WifiP2pConfig();
//            config.deviceAddress = deviceDTO.getIp();
//            config.wps.setup = WpsInfo.PBC;
//            config.groupOwnerIntent = 4;
//            wifiP2pManager.connect(wifip2pChannel, config, new WifiP2pManager.ActionListener() {
//                @Override
//                public void onSuccess() {
//                    // Connection request succeeded. No code needed here
//                }
//
//                @Override
//                public void onFailure(int reasonCode) {
//
//                }
//            });
//        } else {
//            selectedDevice = deviceDTO;
////            showServiceSelectionDialog();
//            DialogUtils.getServiceSelectionDialog(SelectPrintActivity.this, deviceDTO).show();
//        }
        Intent intent = new Intent();
        intent.putExtra("deviceDTO",deviceDTO);
        setResult(RESULT_OK,intent);
        finish();
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case DialogUtils.CODE_PICK_IMAGE:
                if (resultCode == RESULT_OK) {
                    Uri imageUri = data.getData();
                    DataSender.sendFile(SelectPrintActivity.this, selectedDevice.getIp(),
                            selectedDevice.getPort(), imageUri);
                }
                break;
            default:
                break;
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults[0] == PackageManager.PERMISSION_DENIED) {
            finish();
        }
    }
    @Override
    protected void onResume() {
        super.onResume();

        IntentFilter localFilter = new IntentFilter();
        localFilter.addAction(DataHandler.DEVICE_LIST_CHANGED);
        localFilter.addAction(FIRST_DEVICE_CONNECTED);
        localFilter.addAction(DataHandler.CHAT_REQUEST_RECEIVED);
        localFilter.addAction(DataHandler.CHAT_RESPONSE_RECEIVED);
        LocalBroadcastManager.getInstance(SelectPrintActivity.this).registerReceiver(localDashReceiver,
                localFilter);

        IntentFilter wifip2pFilter = new IntentFilter();
        wifip2pFilter.addAction(WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION);
        wifip2pFilter.addAction(WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION);
        wifip2pFilter.addAction(WifiP2pManager.WIFI_P2P_CONNECTION_CHANGED_ACTION);
        wifip2pFilter.addAction(WifiP2pManager.WIFI_P2P_THIS_DEVICE_CHANGED_ACTION);

        wiFiDirectBroadcastReceiver = new WiFiDirectBroadcastReceiver(wifiP2pManager,
                wifip2pChannel, this);
        registerReceiver(wiFiDirectBroadcastReceiver, wifip2pFilter);

        LocalBroadcastManager.getInstance(this).sendBroadcast(new Intent(DataHandler.DEVICE_LIST_CHANGED));
    }

    @Override
    protected void onPause() {
//        if (mNsdHelper != null) {
//            mNsdHelper.stopDiscovery();
//        }
        LocalBroadcastManager.getInstance(this).unregisterReceiver(localDashReceiver);
        unregisterReceiver(wiFiDirectBroadcastReceiver);
        super.onPause();
    }
    public void findPeers() {

        if (!isWDConnected) {

            wifiP2pManager.discoverPeers(wifip2pChannel, new WifiP2pManager.ActionListener() {
                @Override
                public void onSuccess() {
                    NotificationToast.showToast(SelectPrintActivity.this, "Peer discovery started");
                }

                @Override
                public void onFailure(int reasonCode) {
                    NotificationToast.showToast(SelectPrintActivity.this, "Peer discovery failure: "
                            + reasonCode);
                }
            });
        }
    }

    @Override
    protected void onDestroy() {
//        mNsdHelper.tearDown();
//        connListener.tearDown();
        appController.stopConnectionListener();
        Utility.clearPreferences(SelectPrintActivity.this);
        Utility.deletePersistentGroups(wifiP2pManager, wifip2pChannel);
        DBAdapter.getInstance(SelectPrintActivity.this).clearDatabase();
        wifiP2pManager.removeGroup(wifip2pChannel, new WifiP2pManager.ActionListener() {
            @Override
            public void onSuccess() {

            }

            @Override
            public void onFailure(int i) {

            }
        });

        super.onDestroy();
    }

    private BroadcastReceiver localDashReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            switch (intent.getAction()) {
                case FIRST_DEVICE_CONNECTED:
//                    connListener.tearDown();
//                    int newPort = ConnectionUtils.getPort(LocalDashWiFiDirect.this);
//                    connListener = new ConnectionListener(LocalDashWiFiDirect.this,
//                            newPort);
//                    connListener.start();
//                    appController.stopConnectionListener();
//                    appController.startConnectionListener(ConnectionUtils.getPort(LocalDashWiFiDirect.this));
                    appController.restartConnectionListenerWith(ConnectionUtils.getPort(SelectPrintActivity.this));

                    String senderIP = intent.getStringExtra(KEY_FIRST_DEVICE_IP);
                    int port = DBAdapter.getInstance(SelectPrintActivity.this).getDevice
                            (senderIP).getPort();
                    DataSender.sendCurrentDeviceData(SelectPrintActivity.this, senderIP, port, true);
                    isWDConnected = true;
                    break;
                case DataHandler.DEVICE_LIST_CHANGED:
                    ArrayList<DeviceDTO> devices = DBAdapter.getInstance(SelectPrintActivity.this)
                            .getDeviceList();
                    int peerCount = (devices == null) ? 0 : devices.size();
                    if (peerCount > 0) {
                        progressBarLocalDash.setVisibility(View.GONE);
                        deviceListFragment = new PeerListFragment();
                        Bundle args = new Bundle();
                        args.putSerializable(PeerListFragment.ARG_DEVICE_LIST, devices);
                        deviceListFragment.setArguments(args);

                        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                        ft.replace(R.id.deviceListHolder, deviceListFragment);
                        ft.setTransition(FragmentTransaction.TRANSIT_NONE);
                        ft.commit();
                    }

                    break;
                case DataHandler.CHAT_REQUEST_RECEIVED:
                    DeviceDTO chatRequesterDevice = (DeviceDTO) intent.getSerializableExtra(DataHandler
                            .KEY_CHAT_REQUEST);
                    DialogUtils.getChatRequestDialog(SelectPrintActivity.this,
                            chatRequesterDevice).show();
                    break;
                case DataHandler.CHAT_RESPONSE_RECEIVED:
                    boolean isChatRequestAccepted = intent.getBooleanExtra(DataHandler
                            .KEY_IS_CHAT_REQUEST_ACCEPTED, false);
                    if (!isChatRequestAccepted) {
                        NotificationToast.showToast(SelectPrintActivity.this, "Chat request " +
                                "rejected");
                    } else {
                        DeviceDTO chatDevice = (DeviceDTO) intent.getSerializableExtra(DataHandler
                                .KEY_CHAT_REQUEST);
                        DialogUtils.openChatActivity(SelectPrintActivity.this, chatDevice);
                        NotificationToast.showToast(SelectPrintActivity.this, chatDevice
                                .getPlayerName() + "Accepted Chat request");
                    }
                    break;
                default:
                    break;
            }
        }
    };


}
