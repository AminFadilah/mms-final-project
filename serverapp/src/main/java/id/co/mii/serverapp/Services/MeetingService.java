package id.co.mii.serverapp.services;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import id.co.mii.serverapp.models.Attendance;
import id.co.mii.serverapp.models.Meeting;
import id.co.mii.serverapp.models.Participant;
import id.co.mii.serverapp.models.Room;
import id.co.mii.serverapp.models.Status;
import id.co.mii.serverapp.models.TrackingHistory;
import id.co.mii.serverapp.models.User;
import id.co.mii.serverapp.models.dto.request.MeetingDTO;
import id.co.mii.serverapp.repositories.AttendanceRepository;
import id.co.mii.serverapp.repositories.MeetingRepository;
import id.co.mii.serverapp.repositories.ParticipantRepository;
import id.co.mii.serverapp.repositories.RoomRepository;
import id.co.mii.serverapp.repositories.StatusRepository;
import id.co.mii.serverapp.repositories.UserRepository;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class MeetingService {
        private MeetingRepository meetingRepository;
        private RoomRepository roomRepository;
        private StatusRepository statusRepository;
        private ParticipantRepository participantRepository;
        private TrackingHistoryService trackingHistoryService;
        private MailService mailService;
        private AttendanceRepository attendanceRepository;
        private UserRepository userRepository;
        private RoomService roomService;

        public List<Meeting> getAll() {

                return meetingRepository.findAll();
        }

        public List<Meeting> getAllUpcoming(Principal principal) {

                List<Meeting> upcoming = new ArrayList<>();
                LocalDateTime currentDateTime = LocalDateTime.now();
                String username = principal.getName();

                User user = userRepository.findByUsername(username);
                Integer idUser = user.getId();

                for (Meeting meeting : meetingRepository.findAll()) {
                        boolean isAttendee = meeting.getAttendances().stream()
                                        .map(Attendance::getParticipant)
                                        .map(Participant::getId)
                                        .anyMatch(attendeeId -> attendeeId == idUser);

                        if (isAttendee) {
                                if (meeting.getStartMeeting().isAfter(currentDateTime) &&
                                                meeting.getStatus().getId() != 3) {

                                        upcoming.add(meeting);
                                }

                                if (meeting.getStartMeeting().isBefore(currentDateTime)
                                                && meeting.getEndMeeting().isAfter(currentDateTime) &&
                                                meeting.getStatus().getId() != 3) {

                                        upcoming.add(meeting);
                                }
                        }

                }
                return upcoming;
        }

        public List<Meeting> getAllPast(Principal principal) {

                List<Meeting> past = new ArrayList<>();

                LocalDateTime currentDateTime = LocalDateTime.now();

                String username = principal.getName();

                User user = userRepository.findByUsername(username);
                Integer idUser = user.getId();

                for (Meeting meeting : meetingRepository.findAll()) {
                        boolean isAttendee = meeting.getAttendances().stream()
                                        .map(Attendance::getParticipant)
                                        .map(Participant::getId)
                                        .anyMatch(attendeeId -> attendeeId == idUser);
                        if (isAttendee) {

                                if (meeting.getEndMeeting().isBefore(currentDateTime)
                                                && meeting.getStatus().getId() != 3) {
                                        Status status = statusRepository.findById(2)
                                                        .orElseThrow(() -> new EntityNotFoundException(
                                                                        "Status with ID 2 not found"));

                                        List<TrackingHistory> existingTrackingHistory = trackingHistoryService
                                                        .findByMeetingAndStatus(meeting, status);
                                        if (existingTrackingHistory.isEmpty()) {
                                                TrackingHistory trackingHistory = new TrackingHistory();
                                                trackingHistory.setDate(LocalDateTime.now());
                                                trackingHistory.setMeeting(meeting);
                                                trackingHistory.setStatus(status);
                                                // Set participant (PIC) based on the meeting's initiator
                                                trackingHistory.setParticipant(meeting.getInitiator());
                                                trackingHistoryService.create(trackingHistory);
                                        }

                                        meeting.setStatus(status);
                                        past.add(meeting);
                                }
                        }
                }
                return past;
        }

        public List<Meeting> getAllDelete(Principal principal) {

                List<Meeting> cancelledMeetings = new ArrayList<>();
                String username = principal.getName();

                User user = userRepository.findByUsername(username);
                Integer idUser = user.getId();
                System.out.println(idUser);
                for (Meeting meeting : getAll()) {
                        boolean isAttendee = meeting.getAttendances().stream()
                                        .map(Attendance::getParticipant)
                                        .map(Participant::getId)
                                        .anyMatch(attendeeId -> attendeeId == idUser);
                        if (isAttendee && meeting.getStatus().getId() == 3) {

                                cancelledMeetings.add(meeting);

                        }

                }

                return cancelledMeetings;

        }

        public Meeting getById(Integer id) {
                return meetingRepository.findById(id).orElse(null);
        }

        @Transactional
        public Meeting create(MeetingDTO meetingDTO, Principal principal) {
                Meeting meeting = new Meeting();
                meeting.setName(meetingDTO.getName());
                meeting.setDescription(meetingDTO.getDescription());
                meeting.setStartMeeting(meetingDTO.getStartMeeting());
                meeting.setEndMeeting(meetingDTO.getEndMeeting());
                meeting.setLinkRoom(meetingDTO.getLinkRoom());
                meeting.setIsOnline(meetingDTO.getIsOnline());

                if (meetingDTO.getIsOnline()) {
                        meeting.setRoom(null);
                } else {
                        Room room = roomService.getById(meetingDTO.getRoomId());
                        roomService.updateRoomAvailability(room, false);

                        meeting.setRoom(room);
                }

                // Get the existing Status with ID 1 from the database
                Status status = statusRepository.findById(1)
                                .orElseThrow(() -> new EntityNotFoundException("Status with ID 1 not found"));
                meeting.setStatus(status);

                // Get the currently logged-in user's username
                String username = principal.getName();

                User user = userRepository.findByUsername(username);
                Integer idUser = user.getId();

                Participant initiator = participantRepository.findById(idUser)
                                .orElseThrow(() -> new EntityNotFoundException(
                                                "Note Taker with specified ID not found"));
                meeting.setInitiator(initiator);

                Participant noteTaker = participantRepository.findById(meetingDTO.getNoteTakerId())
                                .orElseThrow(() -> new EntityNotFoundException(
                                                "Note Taker with specified ID not found"));
                meeting.setNoteTaker(noteTaker);

                List<Attendance> attendances = new ArrayList<>();

                for (Integer attendeeId : meetingDTO.getAttendees()) {
                        // Get the existing Participant with the specified ID from the database
                        Participant attendee = participantRepository.findById(attendeeId)
                                        .orElseThrow(() -> new EntityNotFoundException(
                                                        "Attendee with specified ID not found"));

                        Attendance attendance = new Attendance();
                        attendance.setParticipant(attendee);
                        attendance.setMeeting(meeting);

                        attendances.add(attendance);
                }

                // Save all the attendances in one go
                attendances = attendanceRepository.saveAll(attendances);

                // Set the list of attendances for the meeting
                meeting.setAttendances(attendances);
                System.out.println(meeting.getStartMeeting());

                // Kirim undangan ke peserta
                for (Integer attendeeId : meetingDTO.getAttendees()) {
                        Participant attendee = participantRepository.findById(attendeeId)
                                        .orElseThrow(() -> new RuntimeException(
                                                        "Attendee with specified ID not found"));

                        // Generate dan kirim file ICS Google Calendar ke email peserta
                        mailService.generateIcsFile(meeting, attendee.getEmail());
                }

                Meeting createdMeeting = meetingRepository.save(meeting);

                // Create TrackingHistory
                TrackingHistory trackingHistory = new TrackingHistory();
                trackingHistory.setDate(LocalDateTime.now());
                trackingHistory.setMeeting(createdMeeting);
                trackingHistory.setStatus(status);
                // Set participant (PIC) based on the meeting's initiator
                trackingHistory.setParticipant(initiator);
                trackingHistoryService.create(trackingHistory);

                return createdMeeting;
        }

        public Meeting update(Integer id, MeetingDTO meetingDTO, Principal principal) {
                Meeting existingMeeting = getById(id);

                if (existingMeeting == null) {
                        throw new EntityNotFoundException("Meeting not found with ID: " + id);
                }

                existingMeeting.setName(meetingDTO.getName());
                existingMeeting.setDescription(meetingDTO.getDescription());
                existingMeeting.setStartMeeting(meetingDTO.getStartMeeting());
                existingMeeting.setEndMeeting(meetingDTO.getEndMeeting());
                existingMeeting.setLinkRoom(meetingDTO.getLinkRoom());
                existingMeeting.setIsOnline(meetingDTO.getIsOnline());

                // String username = principal.getName();

                if (meetingDTO.getIsOnline()) {
                        existingMeeting.setRoom(null);
                } else {
                        Room room = roomService.getById(meetingDTO.getRoomId());
                        existingMeeting.setRoom(room);
                }

                Status status = statusRepository.findById(4)
                                .orElseThrow(() -> new RuntimeException(
                                                "Status not found with ID: 4"));
                existingMeeting.setStatus(status);

                // Get the currently logged-in user's username
                String username = principal.getName();

                User user = userRepository.findByUsername(username);
                Integer idUser = user.getId();

                Participant initiator = participantRepository.findById(idUser)
                                .orElseThrow(() -> new EntityNotFoundException(
                                                "Note Taker with specified ID not found"));
                existingMeeting.setInitiator(initiator);

                Participant noteTaker = participantRepository.findById(meetingDTO.getNoteTakerId())
                                .orElseThrow(
                                                () -> new RuntimeException("Note Taker not found with ID: "
                                                                + meetingDTO.getNoteTakerId()));
                existingMeeting.setNoteTaker(noteTaker);

                List<Attendance> existingAttendances = existingMeeting.getAttendances();
                System.out.println(existingAttendances.size());

                List<Integer> newAttendeeIds = new ArrayList<>(meetingDTO.getAttendees());
                System.out.println(newAttendeeIds);

                Iterator<Attendance> iterator = existingAttendances.iterator();
                while (iterator.hasNext()) {
                        Attendance attendance = iterator.next();
                        System.out.println("cek error");
                        if (!newAttendeeIds.contains(attendance.getParticipant().getId())) {
                                System.out.println(attendance.getId());
                                System.out.println("ini hapus");
                                attendanceRepository.deleteById(attendance.getId());
                                iterator.remove(); // Menghapus elemen dari list menggunakan iterator
                        }
                }

                // Looping untuk menambahkan entri Attendance baru
                for (Integer attendeeId : newAttendeeIds) {
                        // Cek apakah partisipan sudah ada dalam existingAttendances
                        boolean alreadyAttending = existingAttendances.stream()
                                        .anyMatch(attendance -> attendance.getParticipant().getId().equals(attendeeId));

                        if (!alreadyAttending) {
                                System.out.println("ini tambah");
                                // Get the existing Participant with the specified ID from the database
                                Participant attendee = participantRepository.findById(attendeeId)
                                                .orElseThrow(() -> new EntityNotFoundException(
                                                                "Attendee with specified ID not found"));

                                Attendance newAttendance = new Attendance();
                                newAttendance.setParticipant(attendee);
                                newAttendance.setMeeting(existingMeeting);

                                existingAttendances.add(newAttendance);
                        }
                }

                // Simpan perubahan pada Attendance
                attendanceRepository.saveAll(existingAttendances);

                // Simpan perubahan pada pertemuan
                existingMeeting.setAttendances(existingAttendances);
                meetingRepository.save(existingMeeting);

                // Kirim undangan ke peserta
                for (Integer attendeeId : meetingDTO.getAttendees()) {
                        Participant attendee = participantRepository.findById(attendeeId)
                                        .orElseThrow(() -> new RuntimeException(
                                                        "Attendee with specified ID not found"));

                        // Generate dan kirim file ICS Google Calendar ke email peserta
                        mailService.generateIcsFile(existingMeeting, attendee.getEmail());
                }

                TrackingHistory th = new TrackingHistory();
                th.setDate(LocalDateTime.now());
                th.setMeeting(existingMeeting);
                th.setStatus(status);
                th.setParticipant(initiator);
                trackingHistoryService.create(th);

                return meetingRepository.save(existingMeeting);

        }

        public Meeting delete(Integer id, Principal principal) {
                String username = principal.getName();

                User user = userRepository.findByUsername(username);
                Integer idUser = user.getId();

                Participant user2 = participantRepository.findById(idUser)
                                .orElseThrow(() -> new EntityNotFoundException(
                                                "Note Taker with specified ID not found"));

                Meeting meeting = getById(id);
                if (meeting == null) {
                        throw new EntityNotFoundException("Meeting not found with ID: " + id);
                }
                Status status = new Status();
                status.setId(3);
                meeting.setStatus(status);
                TrackingHistory trackingHistory = new TrackingHistory();
                trackingHistory.setDate(LocalDateTime.now());
                trackingHistory.setMeeting(meeting);
                trackingHistory.setStatus(status);
                trackingHistory.setParticipant(user2);
                trackingHistoryService.create(trackingHistory);
                return meeting;
        }

        public Meeting restoreDeletedMeeting(Integer id, Principal principal) {
                String username = principal.getName();
                User user = userRepository.findByUsername(username);
                Integer idUser = user.getId();

                Participant user2 = participantRepository.findById(idUser)
                                .orElseThrow(() -> new EntityNotFoundException(
                                                "Note Taker with specified ID not found"));

                Meeting meeting = getById(id);
                if (meeting == null) {
                        throw new EntityNotFoundException("Meeting not found with ID: " + id);
                }

                // Check if the meeting was actually deleted
                if (meeting.getStatus().getId() != 3) {
                        throw new IllegalArgumentException("Meeting is not in a deleted state.");
                }

                Status restoredStatus = statusRepository.findById(2)
                                .orElseThrow(() -> new EntityNotFoundException("Status 'Restored' not found."));

                meeting.setStatus(restoredStatus);

                TrackingHistory trackingHistory = new TrackingHistory();
                trackingHistory.setDate(LocalDateTime.now());
                trackingHistory.setMeeting(meeting);
                trackingHistory.setStatus(restoredStatus);
                trackingHistory.setParticipant(user2);
                trackingHistoryService.create(trackingHistory);

                return meeting;
        }

        public boolean isRoomUsedInUpcomingMeetings(Room room, Meeting currentMeeting) {
                List<Meeting> upcoming = new ArrayList<>();
                LocalDateTime currentDateTime = LocalDateTime.now();
                System.out.println("isroomused brooo!");

                for (Meeting meeting : meetingRepository.findAll()) {

                        if (meeting.getStartMeeting().isAfter(currentDateTime) &&
                                        meeting.getStatus().getId() != 3) {

                                upcoming.add(meeting);
                        }

                        if (meeting.getStartMeeting().isBefore(currentDateTime)
                                        && meeting.getEndMeeting().isAfter(currentDateTime) &&
                                        meeting.getStatus().getId() != 3) {

                                upcoming.add(meeting);
                        }     

                }

                for (Meeting meeting : upcoming) {
                        if (meeting.getId().equals(currentMeeting.getId())) {
                                continue;
                        }

                        // Check if the room is used in this upcoming meeting
                        if (meeting.getRoom() != null && meeting.getRoom().getId().equals(room.getId())) {
                                return true;
                        }
                }

                return false;
        }

}
