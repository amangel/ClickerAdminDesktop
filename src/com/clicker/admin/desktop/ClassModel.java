package com.clicker.admin.desktop;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class ClassModel {
    
    private static ClassModel _instance;
    private Map<String, ArrayList<String>> groupsAndClients; //group name, <clients in group>
        
    private ClassModel() {
        groupsAndClients = Collections.synchronizedMap(new HashMap<String, ArrayList<String>>());
    }
    
    public static synchronized ClassModel getInstance() {
        if(_instance == null) {
            _instance = new ClassModel();
        }
        return _instance;
    }
    
    public ArrayList<String> getGroups(){
        ArrayList<String> groups = new ArrayList<String>();
        for (String group : groupsAndClients.keySet()) {
            groups.add(group);
        }
        return groups;
    }
    
    public ArrayList<String> getClients(String group){
        return groupsAndClients.get(group);
    }
    
    public void setClientsIntoGroups(String setString) {
        System.out.println("model recieved clients and groups: \n" + setString);
        String[] groupStrings = setString.split("`/&");
        for (int i=0; i<groupStrings.length; i++) {
            String[] groupMembers = groupStrings[i].split("`/;");
            ArrayList<String> newGroupListing = new ArrayList<String>();
            if (groupMembers.length > 1) {
                String[] newMembers = groupMembers[1].split("`/,");
                Collections.addAll(newGroupListing, newMembers);
            }
            groupsAndClients.put(groupMembers[0], newGroupListing);
        }
        System.out.println(groupsAndClients);
    }
}
