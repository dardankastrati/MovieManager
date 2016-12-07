
package ch.hearc.ig.odi.moviemanager.presentation;

import ch.hearc.ig.odi.moviemanager.business.Movie;
import ch.hearc.ig.odi.moviemanager.business.Person;
import ch.hearc.ig.odi.moviemanager.service.Services;
import java.io.Serializable;
import java.util.List;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author dardan.kastrati
 */
@Named("personBean")
@ViewScoped
public class PersonBean implements Serializable {
    @Inject
    Services service;
    private Long currentPersonID;
    private Person currentPerson;
    
    public PersonBean(){
        
    }
    
    public Long getCurrentPersonID() {
        return currentPersonID;
    }

    public void setCurrentPersonID(Long currentPersonID) {
        this.currentPersonID = currentPersonID;
        currentPerson = service.getPersonWithId(currentPersonID);
    }

    public Person getCurrentPerson() {
        return currentPerson;
    }

    public void setCurrentPerson(Person currentPerson) {
        this.currentPerson = currentPerson;
    }
    
    /**
     * Retrieves the movie object corresponding to the request's parameter id
     *
     */
    public void initPerson() {
        String idParam = FacesContext
                .getCurrentInstance()
                .getExternalContext()
                .getRequestParameterMap().get("id");
        if (!(idParam == null || idParam.isEmpty())) {
            currentPersonID = new Long(idParam);
            currentPerson = service.getPersonWithId(currentPersonID);
        }
    }
    
    public String delete(Movie movie){
        service.getPersonWithId(currentPersonID).getMovies().remove(movie);
        return "moviesList.xhtml?id=" + currentPersonID;
        //return "moviesList.xhtml?faces-redirect=true";
    }
    
    public List<Person> getPeopleList(){
        return service.getPeopleList();
    }
    
    public String nav(String dest){
        return dest;
    }
}
