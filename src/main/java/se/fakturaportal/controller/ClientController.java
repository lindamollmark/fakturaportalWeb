package se.fakturaportal.controller;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import se.fakturaportal.core.model.Client;
import se.fakturaportal.core.model.User;
import se.fakturaportal.core.service.ClientService;

import javax.servlet.http.HttpSession;
import java.util.List;
/**
 * Class for saving the customers/client for the user.
 */
@RestController
public class ClientController {

    @Autowired
    private ClientService clientService;

    /**
     * Adds a new client
     * @param client the client to be saved
     * @return the saved client
     */
    @RequestMapping(value = "/views/newClient", method = RequestMethod.POST)
    public String newClient(@RequestBody String client, HttpSession session){
        User user = (User) session.getAttribute("user");
        Client aClient = new Gson().fromJson(client, Client.class);
        aClient.setUser(user);
        aClient = clientService.newClient(aClient);
        return new Gson().toJson(aClient);
    }

    /**
     * Fetches the clientList from the database
     * @return the list of clients
     */
    @RequestMapping(value = "/views/clientlist", method = RequestMethod.GET)
    public String clientList(HttpSession session){
        User user = (User) session.getAttribute("user");
        List<Client> clients = clientService.getClients(user);
        return new Gson().toJson(clients);
    }

    /**
     * Gets a specific client
     * @param clientId the Id for the client to fetch.
     * @return the client to return for the user.
     */
    @RequestMapping(value = "/views/clientView", method = RequestMethod.POST)
    public String clientView(@RequestBody String clientId){
        Client clientID = new Gson().fromJson(clientId, Client.class);
        Client theClient = clientService.fetchClient(clientID.getId());
        return new Gson().toJson(theClient);
    }

    /**
     * Deletes a specific client, if a client has invoices, you can't delete them.
     * @param clientId for the client to be deleted
     * @return a boolean (true/false) if the client has been deleted or not.
     */
    @RequestMapping(value = "/views/deleteClient", method = RequestMethod.POST)
    public boolean deleteClient(@RequestBody String clientId){
        Client aClient = new Gson().fromJson(clientId, Client.class);
        return clientService.deleteClient(aClient.getId());
    }

    /**
     * Updates the information on a client
     * @param client the clientinformation.
     * @return the updated client
     */
    @RequestMapping(value = "/views/updateClient", method = RequestMethod.POST)
    public String updateClient(@RequestBody String client){
        Client aClient = new Gson().fromJson(client, Client.class);
        Client updated = clientService.updateClient(aClient);
        return new Gson().toJson(updated);
    }

    /**
     * Method to investigate if a clientNo is avalible or not.
     * If someone else already has the clientNo you can't make another client with the same number.
     * @param clientNo the number you wish to give your new client.
     * @return true if the number is free to use, false if it's already taken.
     */
    @RequestMapping(value = "views/clientNo", method = RequestMethod.POST)
    public Boolean checkClientNo(@RequestBody String clientNo, HttpSession session){
        User user = (User) session.getAttribute("user");
        return clientService.checkClientNo(Integer.parseInt(clientNo), user);
    }
}
