package ch.hearc.ig.odi.moviemanager.presentation;

import ch.hearc.ig.odi.moviemanager.business.Movie;
import ch.hearc.ig.odi.moviemanager.business.Person;
import ch.hearc.ig.odi.moviemanager.service.Services;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
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

    public PersonBean() {
        this.movieSelect = new Movie();
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

    public Movie getMovieSelect() {
        return movieSelect;
    }

    public void setMovieSelect(Movie movieSelect) {
        this.movieSelect = movieSelect;
    }

    /**
     * Retrieves the person object corresponding to the request's parameter id
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

    public String addMovie() {
        if (movieSelect != null) {
            currentPerson.getMovies().add(movieSelect);
            return "moviesList.xhtml?faces-redirect=true&id=" + currentPersonID;
        } else {
            return "moviesList.xhtml?faces-redirect=true&id=" + currentPersonID;
        }
    }

    public String delete(Movie movie) {
        service.getPersonWithId(currentPersonID).getMovies().remove(movie);
        return "moviesList.xhtml?faces-redirect=true&id=" + currentPersonID;
    }

    public List<Person> getPeopleList() {
        return service.getPeopleList();
    }

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

    public String nav(String dest) {
        return dest;
    }
}
