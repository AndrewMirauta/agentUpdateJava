/**
 * Author: Andrew Mirauta
 * Date: September 12
 * Program: a program that receives an agent object from a database
 * and allows it to be edited by a user. Once edited it also allows the
 * user to save their changes to the database.
 */
package sample;

import java.sql.*;

public class Agents {
    //a class that sets up an agent object
    private int agentId;
    private String agentFirst;
    private String agentMiddle;
    private String agentLast;
    private String bPhone;
    private String email;
    private String position;
    private int agency;
    //agent constructor
    public Agents(int agentId, String agentFirst, String agentMiddle, String agentLast, String bPhone, String email, String position, int agency) {
        this.agentId = agentId;
        this.agentFirst = agentFirst;
        this.agentMiddle = agentMiddle;
        this.agentLast = agentLast;
        this.bPhone = bPhone;
        this.email = email;
        this.position = position;
        this.agency = agency;
    }

    //method that gets agent id to fill combo box later on
    public int getAgentId() {
        return agentId;
    }
    //getters and setters
    public void setAgentId(int agentId) {
        this.agentId = agentId;
    }

    public String getAgentFirst() {
        return agentFirst;
    }

    public void setAgentFirst(String agentFirst) {
        this.agentFirst = agentFirst;
    }

    public String getAgentLast() {
        return agentLast;
    }

    public void setAgentLast(String agentLast) {
        this.agentLast = agentLast;
    }

    public String getAgentMiddle() {
        return agentMiddle;
    }

    public void setAgentMiddle(String agentMiddle) {
        this.agentMiddle = agentMiddle;
    }

    public String getbPhone() {
        return bPhone;
    }

    public void setbPhone(String bPhone) {
        this.bPhone = bPhone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public int getAgency() {
        return agency;
    }

    public void setAgency(int agency) {
        this.agency = agency;
    }

    @Override
    public String toString() {
        return agentId + "";
    }
}

