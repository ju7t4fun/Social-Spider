package com.epam.lab.spider.job.util;

import com.epam.lab.spider.controller.vk.Parameters;
import com.epam.lab.spider.controller.vk.VKException;
import com.epam.lab.spider.controller.vk.Vkontakte;
import com.epam.lab.spider.model.db.entity.Owner;
import com.epam.lab.spider.model.db.service.*;
import com.epam.lab.spider.model.vk.Group;
import com.epam.lab.spider.model.vk.User;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shell on 6/27/2015.
 */
public class OpenedOwnerUpdater {
    public static final Logger LOG = Logger.getLogger(OpenedOwnerUpdater.class);
    private static final ServiceFactory factory = ServiceFactory.getInstance();
    private static final OwnerService ownerService = factory.create(OwnerService.class);

    public static void updateAllOwners(){
        int changeCount = 0;
        List<Owner> owners = ownerService.getLimited(0, 1000);
        StringBuilder usersStringBuilder, groupStringBuilder ;
        usersStringBuilder = new StringBuilder();
        groupStringBuilder = new StringBuilder();
        List<Owner> usersWall = new ArrayList<>();
        List<Owner> groupsWall = new ArrayList<>();
        for(Owner owner:owners){
            Integer id = owner.getVk_id();
            if(id>0){
                usersWall.add(owner);
                usersStringBuilder.append(id).append(",");
            }else {
                groupsWall.add(owner);
                groupStringBuilder.append(-id).append(",");
            }
        }
        Vkontakte vkontakte = new Vkontakte();

        
        

        Parameters parameters;
        // for users
        parameters = new Parameters();
        parameters.add("fields","domain");
        parameters.add("user_ids",usersStringBuilder.substring(0, usersStringBuilder.length()-1));
        try {
            List<User> users = vkontakte.users().get(parameters);
            if(users.size()!=usersWall.size()){
                LOG.fatal("Unequals lists size!");
            }else
            for (int i = 0; i < users.size(); i++) {
                User user = users.get(i);
                Owner owner = usersWall.get(i);

                if(user.getId() != owner.getVk_id().intValue()){
                    LOG.error("Unequals vk.id's!");
                }else{
                    boolean change = false;
                    if(!user.getDomain().equals(owner.getDomain())){
                        change = true;
                        owner.setDomain(user.getDomain());
                    }
                    String ownerName = user.getFirstName()+" "+user.getLastName();
                    if(!ownerName.equals(owner.getName())){
                        change = true;
                        owner.setName(ownerName);
                    }
                    if(change){
                        changeCount++;
                        ownerService.update(owner.getId(),owner);
                    }
                }
            }
        } catch (VKException e) {
            e.printStackTrace();
        }
        // for groups
        parameters = new Parameters();
        parameters.add("group_ids",groupStringBuilder.substring(0, groupStringBuilder.length()-1));
        try {
            List<Group> groups = vkontakte.groups().getById(parameters);
            if(groups.size()!=groupsWall.size()){
                LOG.fatal("Unequals lists size!");
            }else
                for (int i = 0; i < groups.size(); i++) {
                    Group group = groups.get(i);
                    Owner owner = groupsWall.get(i);
                    Integer groupId = - group.get("id").toInt();

                    if(groupId.intValue() != owner.getVk_id().intValue()){
                        LOG.error("Unequals vk.id's!");
                    }else{
                        boolean change = false;
                        String domain = group.get("screen_name").toString();
                        if(!domain.equals(owner.getDomain())){
                            change = true;
                            owner.setDomain(domain);
                        }
                        String ownerName = group.get("name").toString();
                        if(!ownerName.equals(owner.getName())){
                            change = true;
                            owner.setName(ownerName);
                        }
                        if(change){
                            changeCount++;
                            ownerService.update(owner.getId(),owner);
                        }
                    }
                }
        } catch (VKException e) {
            e.printStackTrace();
        }

        LOG.info("Updated data of "+changeCount+" owners.");
    }
    public static void main(String[] args){
        updateAllOwners();
    }
}
