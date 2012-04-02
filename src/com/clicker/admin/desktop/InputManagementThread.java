package com.clicker.admin.desktop;

import java.io.IOException;


public class InputManagementThread {
    
    Thread thread;
    protected boolean threadCanRun;
    private CommunicationHub hub;
    
    public InputManagementThread() {
        threadCanRun = true;
        hub = CommunicationHub.getInstance();
        thread = new Thread( new InputThread() );
        thread.start();
    }
    
    public void stop() {
        threadCanRun = false;
    }
    
    private class InputThread implements Runnable {

        @Override
        public void run() {
            String str = "";
            while (threadCanRun) {
                try {
                    str = hub.readMessage();
                    if (str == null) {
                        hub.gotDisconnected();
                        break;
                    } else if (str.equals("YesImHere")){
                        hub.receivedHeartbeat();
                    } else {
                        System.out.println("Read in:\n\t"+str);
                        String[] parts = str.split(CommunicationHub.GRAVE_SEPARATOR);
                        if (parts[0].equals("AllSets")) {
                            String setData = "";
                            if (parts.length > 0) {
                                setData = parts[1];
                            }
                            allSetsReceived(setData);
                        } else if (parts[0].equals("QuestionSet")) {
                            String setData = "";
                            if (parts.length > 1) {
                                setData = parts[1];
                            }
                            questionSetReceived(setData);
                        } else if (parts[0].equals("ClientList")) {
                            hub.updateGroupsInClassModel(parts[1]);
                        } else if (parts[0].equals("GroupList")) {
                            String[] groupNames = parts[1].split("`/;");
                            updateGroupQuestions(groupNames);
                        } else if (parts[0].equals("DisplayConnected")) {
//                            Log.d("BALLS", str);
//                            String[] consumptionTypes = parts[1].split("`/,");
//                            for (int i=0; i<consumptionTypes.length; i++) {
//                                displaySet.add(consumptionTypes[i]);
//                            }
//                            System.out.println("received : "+str);
                            displayConnected(parts[1]);
                        }
                    }
                    Thread.sleep(100);
                } catch (IOException e) {
                } catch (InterruptedException e) {}
                System.out.println("Server message thread should be dying");
            }
        } 
    }

    public void allSetsReceived(String setData) {
        // TODO Auto-generated method stub
        System.out.println("AllSets: "+setData);
    }

    public void displayConnected(String string) {
        // TODO Auto-generated method stub
        
    }

    public void updateGroupQuestions(String[] groupNames) {
        // TODO Auto-generated method stub
        
    }

    public void questionSetReceived(String setData) {
        // TODO Auto-generated method stub
        
    }
}
