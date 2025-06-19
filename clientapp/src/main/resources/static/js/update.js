// Function untuk mendapatkan tanggal dan waktu saat ini dalam format untuk input datetime-local
function getCurrentDateTime() {
  const now = new Date();
  const year = now.getFullYear();
  const month = String(now.getMonth() + 1).padStart(2, "0");
  const day = String(now.getDate()).padStart(2, "0");
  const hours = String(now.getHours()).padStart(2, "0");
  const minutes = String(now.getMinutes()).padStart(2, "0");
  return `${year}-${month}-${day}T${hours}:${minutes}`;
}

function getCurrentDate() {
  const now = new Date();
  const year = now.getFullYear();
  const month = String(now.getMonth() + 1).padStart(2, "0");
  const day = String(now.getDate()).padStart(2, "0");

  return `${year}-${month}-${day}`;
}

function getCurrentHours() {
  const now = new Date();
  const hours = String(now.getHours()).padStart(2, "0");
  return `${hours}`;
}
function getCurrentMinutes() {
  const now = new Date();
  const minutes = String(now.getMinutes()).padStart(2, "0");
  return `${minutes}`;
}

function combineDateTime(dateInputId, timeInputId) {
  const dateInput = document.getElementById(dateInputId);
  const timeInput = document.getElementById(timeInputId);

  const dateValue = dateInput.value;
  const timeValue = timeInput.value;
  const combinedDateTime = `${dateValue}T${timeValue}:00`;

  return combinedDateTime;
}

$(document).ready(function () {
  // Generate options for hours (00 to 23)
  for (let hour = 0; hour <= 23; hour++) {
    const option = $("<option>")
      .val(hour.toString().padStart(2, "0"))
      .text(hour.toString().padStart(2, "0"));
    $("#startHour, #endHour").append(option);
  }

  // Generate options for minutes (00, 15, 30, 45)
  for (let minute = 0; minute < 60; minute += 15) {
    const option = $("<option>")
      .val(minute.toString().padStart(2, "0"))
      .text(minute.toString().padStart(2, "0"));
    $("#startMinute, #endMinute").append(option);
  }

  $("#meetingDate").val(getCurrentDate());
  $("#startHour").val(getCurrentHours());
  $("#startMinute").val(getCurrentMinutes());
  $("#endHour").val(getCurrentHours());
  $("#endMinute").val(getCurrentMinutes());
});

function updateStartAndEndMeeting() {
  const startHour = $("#startHour").val();
  const startMinute = $("#startMinute").val();
  const endHour = $("#endHour").val();
  const endMinute = $("#endMinute").val();

  const startMeetingValue = `${startHour}:${startMinute}`;
  const endMeetingValue = `${endHour}:${endMinute}`;
  console.log(startMeetingValue);

  $("#start-meeting").val(startMeetingValue);
  $("#end-meeting").val(endMeetingValue);
}

document.addEventListener("DOMContentLoaded", function () {
  const meetingDateInput = document.getElementById("meetingDate");

  const startDateTimeOutput = document.getElementById("startMeeting");
  const endDateTimeOutput = document.getElementById("endMeeting");

  const startTimeInput = document.getElementById("startHour");
  const startMinuteInput = document.getElementById("startMinute");
  const endTimeInput = document.getElementById("endHour");
  const endMinuteInput = document.getElementById("endMinute");

  meetingDateInput.min = getCurrentDateTime().substr(0, 10);

  // Buat fungsi untuk mengupdate waktu saat ada perubahan
  function updateDateStartTime() {
    updateStartAndEndMeeting();
    const combinedStartDateTime = combineDateTime(
      "meetingDate",
      "start-meeting"
    );
    startDateTimeOutput.value = combinedStartDateTime;
  }

  function updateDateEndTime() {
    updateStartAndEndMeeting();
    const combinedEndDateTime = combineDateTime("meetingDate", "end-meeting");
    endDateTimeOutput.value = combinedEndDateTime;

    console.log($("#endMeeting").val());
  }

  // Tambahkan event listener untuk start time
  startTimeInput.addEventListener("change", updateDateStartTime);
  startMinuteInput.addEventListener("change", updateDateStartTime);

  // Tambahkan event listener untuk end time
  endTimeInput.addEventListener("change", updateDateEndTime);
  endMinuteInput.addEventListener("change", updateDateEndTime);

  const selectedAttendees = $("#attendeeSelect").val();
  if (selectedAttendees) {
    loadRooms(selectedAttendees.length);
  }

  function toggleLinkRoomMeeting() {
    const isOnlineSwitch = $("#isOnlineSwitch");
    const linkRoomInput = $("#meetingLinkInput");
    const roomInput = $("#room");
    const isOnlineHiddenInput = $("input[name='isOnline']");

    if (isOnlineSwitch.prop("checked")) {
      linkRoomInput.prop("disabled", false);
      roomInput.prop("disabled", true);
      roomInput.val("");
      isOnlineHiddenInput.val("true");
    } else {
      linkRoomInput.prop("disabled", true);
      linkRoomInput.val("");
      roomInput.prop("disabled", false);
      isOnlineHiddenInput.val("false");
    }
  }

  $(document).ready(function () {
    toggleLinkRoomMeeting();
    $("#isOnlineSwitch").on("change", function () {
      toggleLinkRoomMeeting();
    });
  });
});

function loadAttendees() {
  $.ajax({
    url: "/api/participants",
    method: "GET",
    dataType: "json",
    success: function (data) {
      const attendeeSelect = $("#attendeeSelect");
      data.forEach(function (attendee) {
        const option = new Option(attendee.name, attendee.id, false, false);
        attendeeSelect.append(option);
      });
      attendeeSelect.select2();
      attendeeSelect.on("change", function () {
        updateNoteTakerOptions();
        const selectedAttendees = $(this).val();
        if (selectedAttendees) {
          loadRooms(selectedAttendees.length);
        }
      });
    },
    error: function (xhr, status, error) {
      console.error("Error loading attendees:", error);
    },
  });
}

function updateNoteTakerOptions() {
  const selectedAttendees = $("#attendeeSelect").val();
  const noteTakerSelect = $("#note-taker");

  // Hapus opsi yang sudah ada kecuali opsi "Choose Note Taker"
  noteTakerSelect.find("option:not(:first)").remove();

  // Tambahkan selected attendees sebagai opsi di dropdown Note Taker
  selectedAttendees.forEach(function (attendeeId) {
    const attendeeName = $(
      "#attendeeSelect option[value='" + attendeeId + "']"
    ).text();
    const option = new Option(attendeeName, attendeeId);
    noteTakerSelect.append(option);
  });

  // Refresh Select2 untuk mengupdate tampilan dropdown Note Taker
  noteTakerSelect.trigger("change");
}

function updateSelectedAttendees() {
  const selectedAttendees = $("#attendeeSelect").val();
  $("#selectedAttendees").val(selectedAttendees.join(","));

  const isOnline = $("#isOnlineSwitch").prop("checked");
  const roomInput = $("#room");

  // Jika isOnline bernilai true, set room_id menjadi null
  if (isOnline) {
    roomInput.val(null);
  }
}

$(document).ready(function () {
  loadAttendees();
  updateNoteTakerOptions();

  $("#submitBtn").on("click", function () {
    const name = $("input[name='name']").val();
    const description = $("textarea[name='description']").val();
    const selectedAttendees = $("#attendeeSelect").val();
    const noteTakerId = $("#note-taker").val();
    const meetingDate = $("input[name='meetingDate']").val();
    const startTime = $("input[name='startTime']").val();
    const endTime = $("input[name='endTime']").val();
    const meetingLink = $("input[name='meetingLinkInput']").val();
    const roomId = $("#room").val();

    if (
      name === "" ||
      description === "" ||
      selectedAttendees.length === 0 ||
      noteTakerId === "" ||
      meetingDate === "" ||
      startTime === "" ||
      endTime === "" ||
      meetingLink === ""
    ) {
      const isOnline = $("#isOnlineSwitch").prop("checked");
      if (!isOnline && roomId === "") {
        alert("Please fill in all required fields.");
        $("#room").focus();
      }
      alert("Please fill in all required fields.");

      // Mencari input field yang kosong dan memberikan fokus kepadanya
      if (name === "") {
        $("input[name='name']").focus();
      } else if (description === "") {
        $("textarea[name='description']").focus();
      } else if (selectedAttendees.length === 0) {
        $("#attendeeSelect").focus();
      } else if (noteTakerId === "") {
        $("#note-taker").focus();
      } else if (meetingDate === "") {
        $("input[name='meetingDate']").focus();
      } else if (startTime === "") {
        $("input[name='startTime']").focus();
      } else if (endTime === "") {
        $("input[name='endTime']").focus();
      } else if (meetingLink === "") {
        $("input[name='meetingLinkInput']").focus();
      }

      return false;
    }

    updateSelectedAttendees();
    document.getElementById("overlay").style.display = "flex";
  });
});

function addAttendee() {
  const name = $("#name-create").val();
  const email = $("#email-create").val();
  const phone = $("#phone-create").val();

  // Make an AJAX POST request to add the attendee
  $.ajax({
    type: "POST",
    url: "/api/participants",
    beforeSend: addCsrfToken(),
    data: JSON.stringify({ name: name, email: email, phone: phone }),
    contentType: "application/json",
    success: function (data) {
      // On successful response, add the new attendee to the Select2 dropdown
      const attendeeSelect = $("#attendeeSelect");
      const newAttendeeOption = new Option(data.name, data.id, true, true);
      attendeeSelect.append(newAttendeeOption).trigger("change");

      // Close the modal
      $("#create-external").modal("hide");
    },
    error: function (error) {
      console.error("Error adding attendee:", error.responseText);
    },
  });
}

function loadRooms(selectedAttendeeCount) {
  $.ajax({
    url: "/api/room",
    method: "GET",
    dataType: "json",
    success: function (data) {
      const roomSelect = $("#room");
      roomSelect.empty();

      // Tambahkan opsi default dengan nilai kosong
      const defaultOption = new Option("Select Room", "");
      roomSelect.append(defaultOption);

      data.forEach(function (room) {
        if (room.capacity >= selectedAttendeeCount) {
          const option = new Option(room.name, room.id);
          roomSelect.append(option);
        }
      });
    },
    error: function (xhr, status, error) {
      console.error("Error loading rooms:", error);
    },
  });
}

$(document).ready(function () {
  $(".buttonTrigger").click(function () {
    var meetingId = $(this).data("meeting-id");
    $("#meetingModal" + meetingId).modal("show");
    console.log(meetingId);
    detailAttendee(meetingId);
  });

  function detailAttendee(meetingId) {
    // Your AJAX code here to fetch attendee details
    // For example:
    $.ajax({
      method: "GET",
      url: `/api/attendance/attendees/${meetingId}`,
      dataType: "JSON",
      success: function (result) {
        $("#meetingModal" + meetingId + " #table-attendee tbody").empty();
        result.forEach(function (attendee) {
          $("#meetingModal" + meetingId + " #table-attendee tbody").append(`
              <tr>
                <td>${attendee.name}</td>
              </tr>
            `);
        });
      },
      error: function (error) {
        console.error("Error fetching attendee data:", error);
      },
    });
  }
});
    function loadAttendees() {
      $.ajax({
        url: "/api/participants", // Ganti dengan URL yang sesuai untuk mengambil data attendees
        method: "GET",
        dataType: "json",
        success: function (data) {
          const attendeeSelect = $("#attendeeSelect");
          const selectedParticipantNames =
            $("#attendeeSelect").data("participant-names");
          console.log(selectedParticipantNames);
    
          data.forEach(function (attendee) {
            let isSelected = selectedParticipantNames.includes(attendee.name);
    
            const option = new Option(
              attendee.name,
              attendee.id,
              isSelected,
              isSelected
            );
            attendeeSelect.append(option);
          });
    
          // Initialize the Select2 plugin
          attendeeSelect.select2();
          updateNoteTakerOptions();
    
          // Panggil fungsi untuk update pilihan Note Taker ketika dropdown Attendees berubah
          attendeeSelect.on("change", function () {
            updateNoteTakerOptions();
          });
        },
        error: function (xhr, status, error) {
          console.error("Error loading attendees:", error);
        },
      });
    }
