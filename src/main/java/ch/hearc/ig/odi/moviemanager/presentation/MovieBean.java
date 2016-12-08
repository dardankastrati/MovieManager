package ch.hearc.ig.odi.moviemanager.presentation;

import ch.hearc.ig.odi.moviemanager.business.Movie;
import ch.hearc.ig.odi.moviemanager.business.Person;
import ch.hearc.ig.odi.moviemanager.exception.NullParameterException;
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
@Named("movieBean")
@ViewScoped
public class MovieBean implements Serializable {

    @Inject
    Services service;
    private Long currentMovieID;
    private Movie currentMovie;

    public MovieBean() {        
        if(currentMovie == null){
            currentMovie = new Movie();
        }
    }

    public Long getCurrentMovieID() {
        return currentMovieID;
    }

    public void setCurrentMovieID(Long currentMovieID) {
        this.currentMovieID = currentMovieID;
        currentMovie = service.getMovieWithId(currentMovieID);
    }

    public Movie getCurrentMovie() {
        return currentMovie;
    }

    public void setCurrentMovie(Movie currentMovie) {
        this.currentMovie = currentMovie;
    }

    public List<Movie> getMoviesList() {
        return service.getMoviesList();
    }

    public int countPeopleOfMovie(Movie movie) {
        int cpt = 0;

        for (Person tmpPerson : service.getPeopleList()) {
            if (tmpPerson.getMovies().indexOf(movie) >= 0) {
                cpt++;
            }
        }

        return cpt;
    }

    public List<Person> getPersonByMovie() {
        List<Person> listCompletPerson = service.getPeopleList();
        List<Person> listPersonForMovie = new ArrayList<>();

        for (Person currentPerson : listCompletPerson) {
            if (currentPerson.getMovies().indexOf(currentMovie) != -1) {
                listPersonForMovie.add(currentPerson);
            }
        }

        return listPersonForMovie;
    }

    /**
     * Retrieves the movie object corresponding to the request's parameter id
     *
     */
    public void initMovie() {
        String idParam = FacesContext
                .getCurrentInstance()
                .getExternalContext()
                .getRequestParameterMap().get("id");
        if (!(idParam == null || idParam.isEmpty())) {
            currentMovieID = new Long(idParam);
            currentMovie = service.getMovieWithId(currentMovieID);
        }
    }

    public String update() {
        service.getMovieWithId(currentMovieID).setName(currentMovie.getName());
        service.getMovieWithId(currentMovieID).setProducer(currentMovie.getProducer());
        return "/index.xhtml";
    }

    public String add() throws NullParameterException {
        currentMovie.setPeople(new ArrayList<Person>());
        service.saveMovie(currentMovie);
        return "/index.xhtml?faces-redirect=true";
    }

    public String reset() {
        this.currentMovie.setName("");
        this.currentMovie.setProducer("");
        return "edit.xhtml?faces-redirect=true&id=" + currentMovieID;
    }

    public String nav(String dest) {
        if (dest.equals("editMovie")) {
            return "edit.xhtml?faces-redirect=true&id=" + currentMovieID;
        } else if (dest.equals("accueil")) {
            return "/index.xhtml";
        } else if (dest.equals("addMovie")) {
            return "movies/edit.xhtml";
        } else {
            return "movieList.xhtml";
        }
    }
}
