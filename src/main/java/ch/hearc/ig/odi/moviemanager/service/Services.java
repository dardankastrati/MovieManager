package ch.hearc.ig.odi.moviemanager.service;

import ch.hearc.ig.odi.moviemanager.business.Movie;
import ch.hearc.ig.odi.moviemanager.business.Person;
import ch.hearc.ig.odi.moviemanager.exception.InvalidParameterException;
import ch.hearc.ig.odi.moviemanager.exception.NullParameterException;
import ch.hearc.ig.odi.moviemanager.exception.UniqueException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.ejb.Stateful;
import javax.enterprise.context.SessionScoped;

/**
 * Classe de services qui stocke les données de l'application dans la session de l'utilisateur.
 * Cette classe est gérée par CDI et peut donc être injectée dans les autres classes Java.
 * @author julien.plumez
 */
@SessionScoped
@Stateful
public class Services implements Serializable{
    
    private Map<Long, Person> people;
    private Map<Long, Movie> movies;
    
    private Long maxPersonId = 0l;
    private Long maxMovieId = 0l;
    
    /**
     * Initialise la classe de services et crée 6 personnes et 9 films pour avoir des données de test.
     */
    public Services() {
        people = new LinkedHashMap<>();
        maxPersonId++;
        people.put(maxPersonId, new Person(maxPersonId, "Lara", "Clette"));
        maxPersonId++;
        people.put(maxPersonId, new Person(maxPersonId, "Homer", "Dalors"));
        maxPersonId++;
        people.put(maxPersonId, new Person(maxPersonId, "Sarah", "Vigote"));
        maxPersonId++;
        people.put(maxPersonId, new Person(maxPersonId, "Pierre", "Oglyphe"));
        maxPersonId++;
        people.put(maxPersonId, new Person(maxPersonId, "John", "Doeuf"));
        maxPersonId++;
        people.put(maxPersonId, new Person(maxPersonId, "Cécile", "Icone"));
        maxPersonId++;
        people.put(maxPersonId, new Person(maxPersonId, "Jules", "Yainpayable"));
        
        // Liste tirée de http://www.allocine.fr/film/meilleurs/
        movies = new LinkedHashMap<>();
        maxMovieId++;
        movies.put(maxMovieId, new Movie(maxMovieId, "Forrest Gump", "Robert Zemeckis"));
        maxMovieId++;
        movies.put(maxMovieId, new Movie(maxMovieId, "La ligne verte", "Frank Darabont"));
        maxMovieId++;
        movies.put(maxMovieId, new Movie(maxMovieId, "Django Unchained", "Quentin Tarantino"));
        maxMovieId++;
        movies.put(maxMovieId, new Movie(maxMovieId, "Gran Torino", "Clint Eastwood"));
        maxMovieId++;
        movies.put(maxMovieId, new Movie(maxMovieId, "La liste de Schindler", "Steven Spielberg"));
        maxMovieId++;
        movies.put(maxMovieId, new Movie(maxMovieId, "The Dark Knight", "Christopher Nolan"));
        maxMovieId++;
        movies.put(maxMovieId, new Movie(maxMovieId, "Le Parrain", "Francis Ford Coppola"));
        maxMovieId++;
        movies.put(maxMovieId, new Movie(maxMovieId, "Pulp Fiction", "Quentin Tarantino"));
        maxMovieId++;
        movies.put(maxMovieId, new Movie(maxMovieId, "Le seigneur des anneaux, le retour du roi", "Peter Jackson"));
        
        try {
            people.get(1l).addMovie(movies.get(1l));
            people.get(1l).addMovie(movies.get(3l));
            people.get(1l).addMovie(movies.get(5l));
            people.get(1l).addMovie(movies.get(6l));
            people.get(2l).addMovie(movies.get(7l));
            people.get(2l).addMovie(movies.get(8l));
            people.get(2l).addMovie(movies.get(9l));
            people.get(3l).addMovie(movies.get(2l));
            people.get(3l).addMovie(movies.get(4l));
            people.get(3l).addMovie(movies.get(5l));
            people.get(4l).addMovie(movies.get(6l));
            people.get(4l).addMovie(movies.get(8l));
            people.get(4l).addMovie(movies.get(1l));
            people.get(4l).addMovie(movies.get(2l));
            people.get(4l).addMovie(movies.get(3l));
            people.get(4l).addMovie(movies.get(5l));
            people.get(4l).addMovie(movies.get(7l));
            people.get(5l).addMovie(movies.get(3l));
            people.get(5l).addMovie(movies.get(4l));
            people.get(5l).addMovie(movies.get(6l));
            people.get(6l).addMovie(movies.get(7l));
            people.get(6l).addMovie(movies.get(9l));
            people.get(6l).addMovie(movies.get(1l));
            people.get(6l).addMovie(movies.get(2l));
            people.get(7l).addMovie(movies.get(2l));
        } 
        catch (UniqueException | NullParameterException ex) {
            // Ne devrait pas arriver
        }
    }
    
    /**
     * Si l'instance Person passée en paramètre ne contient pas d'ID, crée une nouvelle personne
     * Si un ID est présent, effectue une modification.
     * @param person La personne à insérer ou à mettre à jour
     * @throws ch.hearc.ig.odi.moviemanager.exception.NullParameterException Levée si le paramètre reçu est null
     */
    public void savePerson(Person person) throws NullParameterException{
        if(person == null){
            throw new NullParameterException("The parameter person is null");
        }
        
        if(person.getId() == null){
            maxPersonId++;
            person.setId(maxPersonId);
        }
        
        people.put(person.getId(), person);
    }
    
    /**
     * Si l'instance Movie passée en paramètre ne contient pas d'ID, crée un nouveau film
     * Si un ID est présent, effectue une modification
     * @param movie Le film à insérer ou à mettre à jour
     * @throws ch.hearc.ig.odi.moviemanager.exception.NullParameterException Levée si le paramètre reçu est null
     */
    public void saveMovie(Movie movie) throws NullParameterException{
        if(movie == null){
            throw new NullParameterException("The parameter movie is null");
        }
        
        if(movie.getId() == null){
            maxMovieId++;
            movie.setId(maxMovieId);
        }
        
        movies.put(movie.getId(), movie);
    }
    
    /**
     * Retourne une List contenant toutes les personnes du système.
     * Utile pour l'affichage des personnes dans les facelets
     * @return Une List contenant toutes les personnes du système.
     */
    public List<Person> getPeopleList(){
        return new ArrayList(people.values());
    }
    
    /**
     * Retourne une List contenant tous les films du système
     * Utile pour l'affichage des films dans les facelets
     * @return Une List contenant tous les films du système
     */
    public List<Movie> getMoviesList(){
        return new ArrayList(movies.values());
    }
    
    /**
     * Supprime la relation entre un film et une personne.
     * @param person La personne dont il faut supprimer le film
     * @param movie Le film à supprimer
     * @throws NullParameterException Levée si un des deux paramètres est null
     * @throws InvalidParameterException Levée si la personne ne contient pas le film, ou si le film ne contient pas la personne
     */
    public void removeMovieFromPerson(Person person, Movie movie) throws NullParameterException, InvalidParameterException{
        if(person == null){
            throw new NullParameterException("Person is null");
        }
        
        if(movie == null){
            throw new NullParameterException("Movie is null");
        }
        
        people.get(person.getId()).removeMovie(movies.get(movie.getId()));
    }
    
    /**
     * Crée la relation entre un film et une personne
     * @param person La personne à qui il faut ajouter le film
     * @param movie Le film à ajouter à la personne
     * @throws NullParameterException Levée si un paramètre est null
     * @throws UniqueException Levée si la personne a déjà vu ce film, ou si ce film possède déjà la personne
     */
    public void addMovieToPerson(Person person, Movie movie) throws NullParameterException, UniqueException{
        if(person == null){
            throw new NullParameterException("Person is null");
        }
        
        if(movie == null){
            throw new NullParameterException("Movie is null");
        }
        
        people.get(person.getId()).addMovie(movies.get(movie.getId()));
    }
    
    /**
     * Retourne le film correspondant à l'ID passé en paramètre, ou null si aucune personne ne correspond
     * @param id L'ID du film recherché
     * @return Le film recherché
     */
    public Movie getMovieWithId(Long id){
        return movies.get(id);
    }
    
    /**
     * Retourne la personne correspond à l'ID passé en paramètre, ou null si aucune personne ne correspond
     * @param id l'ID de la personne recherchée
     * @return La personne recherchée
     */
    public Person getPersonWithId(Long id){
        return people.get(id);
    }

    public Map<Long, Person> getPeople() {
        return people;
    }

    public void setPeople(Map<Long, Person> people) {
        this.people = people;
    }

    public Map<Long, Movie> getMovies() {
        return movies;
    }

    public void setMovies(Map<Long, Movie> movies) {
        this.movies = movies;
    }
    
    
}
