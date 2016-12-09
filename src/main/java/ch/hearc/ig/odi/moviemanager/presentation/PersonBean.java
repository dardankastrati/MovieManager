package ch.hearc.ig.odi.moviemanager.presentation;

import ch.hearc.ig.odi.moviemanager.business.Movie;
import ch.hearc.ig.odi.moviemanager.business.Person;
import ch.hearc.ig.odi.moviemanager.exception.NullParameterException;
import ch.hearc.ig.odi.moviemanager.exception.UniqueException;
import ch.hearc.ig.odi.moviemanager.service.Services;
import java.io.Serializable;
import java.util.ArrayList;
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
    private Movie movieSelect;

    /**
     * Le constructeur initialise les variables pour éviter les NullPointerException
     */
    public PersonBean() {
        this.movieSelect = new Movie();
        
        if(currentPerson == null){
            currentPerson = new Person();
        }
    }
    
    /**
     * Getter retour de l'id de la personne
     * @return un (Long)
     */
    public Long getCurrentPersonID() {
        return currentPersonID;
    }

    /**
     * Setter on indique l'id de la personne courrante et on récupère également 
     * la personne courrante à partir de l'ID
     */
    public void setCurrentPersonID(Long currentPersonID) {
        this.currentPersonID = currentPersonID;
        currentPerson = service.getPersonWithId(currentPersonID);
    }
    
    /**
     * Getter retour de la personne courrante
     * @return une personne
     */
    public Person getCurrentPerson() {
        return currentPerson;
    }

    /**
     * Setter on indique la personne courrante
     */
    public void setCurrentPerson(Person currentPerson) {
        this.currentPerson = currentPerson;
    }

    /**
     * Getter retour du film sélectionné dans la liste déroulante
     * @return un film
     */
    public Movie getMovieSelect() {
        return movieSelect;
    }

    /**
     * Setter on indique le film sélectionné dans la liste déroulante
     */
    public void setMovieSelect(Movie movieSelect) {
        this.movieSelect = movieSelect;
    }

    /**
     * Retrouver la personne grâce à l'ID passé en paramètre
     * On initialise l'ID de la personne et on récupère également la personne
     * à partir de l'ID passé en paramètre
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

    /**
     * On ajoute un film à la personne courrante, puis on ajoute la persone 
     * courrante dans la liste de personnes du film
     * @return la navigation sur la même page avec l'ID de la personne en paramètre
     * @throws NullParameterException
     * @throws UniqueException 
     */
    public String addMovie() throws NullParameterException, UniqueException {
        if (movieSelect != null) {
            service.getPersonWithId(currentPersonID).getMovies().add(movieSelect);
            
            if(service.getMovieWithId(movieSelect.getId()).getPeople() == null){
                List<Person> lp = new ArrayList<>();
                lp.add(currentPerson);
                service.getMovieWithId(movieSelect.getId()).setPeople(new ArrayList<Person>());
                service.getMovieWithId(movieSelect.getId()).setPeople(lp);
            } else {
                service.getMovieWithId(movieSelect.getId()).getPeople().add(currentPerson);
            }

            return "moviesList.xhtml?faces-redirect=true&id=" + currentPersonID;
        } else {
            return "moviesList.xhtml?faces-redirect=true&id=" + currentPersonID;
        }
    }

    /**
     * On supprime le film de la liste de la personne
     * @param movie
     * @return la navigation sur la même page avec l'ID de la personne en paramètre
     */
    public String delete(Movie movie) {
        service.getPersonWithId(currentPersonID).getMovies().remove(movie);
        return "moviesList.xhtml?faces-redirect=true&id=" + currentPersonID;
    }
    
    /**
     * On met à jour le nom et le prénom de la personne courrante
     * @return la navigation sur la page accueil
     */
    public String update(){
        service.getPersonWithId(currentPersonID).setFirstName(currentPerson.getFirstName());
        service.getPersonWithId(currentPersonID).setLastName(currentPerson.getLastName());
        return "/index.xhtml?faces-redirect=true";
    }
    
    /**
     * On ajoute ajoute la personne à notre liste
     * @return la navigation sur la page accueil
     * @throws NullParameterException 
     */
    public String add() throws NullParameterException{
        currentPerson.setMovies(new ArrayList<Movie>());
        service.savePerson(currentPerson);
        return "/index.xhtml?faces-redirect=true";
    }

    /**
     * On retourne la liste des personnes enregistées
     * @return une liste de personnes
     */
    public List<Person> getPeopleList() {
        return service.getPeopleList();
    }

    /**
     * On retourne la liste des films que la personne courrante n'a pas vue
     * @return Liste de film
     */
    public List<Movie> getMovieMissing() {
        List<Movie> listCompletMovie = service.getMoviesList();
        List<Movie> listMovieMissing = new ArrayList<>();

        for (Movie currentMovie : listCompletMovie) {
            if (currentPerson.getMovies().indexOf(currentMovie) == -1) {
                listMovieMissing.add(currentMovie);
            }
        }

        return listMovieMissing;
    }

    /**
     * On vide les attributs de la personne
     * @return La navigation permettant de revenir sur la page avec l'ID de la personne en paramètre
     */
    public String reset() {
        this.currentPerson.setFirstName("");
        this.currentPerson.setLastName("");
        return "edit.xhtml?faces-redirect=true&id=" + currentPersonID;
    }

    /**
     * On envoie un paramètre destination qui va nous permettre de définir la navigation
     * @param dest destination
     * @return La navigation à exécuter
     */
    public String nav(String dest) {
        if (dest.equals("editPerson")) {
           return "edit.xhtml?faces-redirect=true&id=" + currentPersonID;
        } else if(dest.equals("accueil")){
            return "/index.xhtml";
        } else {
            return dest;
        }
    }
}
