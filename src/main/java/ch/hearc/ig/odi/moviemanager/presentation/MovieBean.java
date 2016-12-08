package ch.hearc.ig.odi.moviemanager.presentation;

import ch.hearc.ig.odi.moviemanager.business.Movie;
import ch.hearc.ig.odi.moviemanager.business.Person;
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
    
    public List<Person> getPersonByMovie(){
        List<Person> listCompletPerson = service.getPeopleList();
        List<Person> listPersonForMovie = new ArrayList<>();
        
        for(Person currentPerson : listCompletPerson){
            if(currentPerson.getMovies().indexOf(currentMovie) != -1){
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

    public String nav(String dest) {
        if (dest.equals("editMovie")) {
            return "edit.xhtml?faces-redirect=true&id=" + currentMovieID;
        } else {
            return "movieList.xhtml";
        }
    }
}
