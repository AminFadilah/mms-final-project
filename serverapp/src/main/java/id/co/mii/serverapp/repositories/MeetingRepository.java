package id.co.mii.serverapp.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import id.co.mii.serverapp.models.Meeting;
import id.co.mii.serverapp.models.Status;

@Repository
public interface MeetingRepository extends JpaRepository<Meeting, Integer>{
    
}
