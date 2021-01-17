package facades;

import dtos.SportDTO;
import dtos.SportTeamDTO;
import entities.Sport;
import entities.SportTeam;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Joakim Skaarup Stensnæs
 */
public class APIFacade {

    private static APIFacade instance;
    private static EntityManagerFactory emf;

    //Private Constructor to ensure Singleton
    private APIFacade() {
    }


    /**
     * @param _emf
     * @return an instance of this facade class.
     */
    public static APIFacade getSportFacade(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new APIFacade();
        }
        return instance;
    }

    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    /* Sport methods */
    //TODO public SportDTO getSport(String id) {}
    public SportDTO getSportDTO(String id) {
        EntityManager em = emf.createEntityManager();
        Sport sport;
        try {
            sport = em.find(Sport.class, id);
        } finally {
            em.close();
        }
        return new SportDTO(sport);
    }

    public SportDTO addSport(String name, String desc) {
        EntityManager em = emf.createEntityManager();
        Sport sport = new Sport(name, desc);
        try {
            em.getTransaction().begin();
            em.persist(sport);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
        return new SportDTO(sport);
    }

    //TODO public String removeSport(String id) {}
    public void removeSport(String id) {
        EntityManager em = emf.createEntityManager();
        Sport sport;
        try {
            sport = em.find(Sport.class, id);
            em.getTransaction().begin();
            em.remove(sport);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    public List<SportDTO> getAllSports() {
        EntityManager em = emf.createEntityManager();
        List<SportDTO> sportDTOList;
        try {
            TypedQuery<Sport> query = em.createQuery("SELECT s FROM Sport s", Sport.class);
            List<Sport> sports = query.getResultList();
            sportDTOList = new ArrayList<>();
            sports.forEach((Sport sport) -> sportDTOList.add(new SportDTO(sport)));
        } finally {
            em.close();
        }
        return sportDTOList;
    }

    /* SportTeam methods */
    //TODO public SportTeamDTO addSportTeam(SportTeamDTO sportTeamDTO) {}
    public SportTeamDTO addSportTeam(String name, Integer price, Integer minAge, Integer maxAge, String sportString) {
        EntityManager em = emf.createEntityManager();
        Sport sport;
        SportTeam sportTeam;
        try {
            sport = em.find(Sport.class, sportString);
            System.out.println(sport.getSportName());
            sportTeam = new SportTeam(name, price, minAge, maxAge, sport);

            em.getTransaction().begin();
            em.persist(sportTeam);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
        return new SportTeamDTO(sportTeam);
    }

    //TODO public String removeSportTeam(String id) {}
    public void removeSportTeam(String id) {
        EntityManager em = emf.createEntityManager();
        SportTeam sportTeam;
        try {
            sportTeam = em.find(SportTeam.class, id);
            em.getTransaction().begin();
            em.remove(sportTeam);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    //TODO public List<SportTeamDTO> getAllTeams() {}
    public List<SportTeamDTO> getAllTeams() {
        EntityManager em = emf.createEntityManager();
        List<SportTeamDTO> sportTeamDTOList;
        try {
            TypedQuery<SportTeam> query = em.createQuery("SELECT st FROM SportTeam st", SportTeam.class);
            List<SportTeam> sportTeams = query.getResultList();
            sportTeamDTOList = new ArrayList<>();
            sportTeams.forEach((SportTeam sportTeam) -> sportTeamDTOList.add(new SportTeamDTO(sportTeam)));
        } finally {
            em.close();
        }
        return sportTeamDTOList;
    }
}