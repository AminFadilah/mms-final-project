$(document).ready(function () {
  const isAdmin = $('[data-is-admin]').data('isAdmin');
  // console.log($('[data-is-admin]').data('isAdmin'));
  $("#table-room").DataTable({
    ajax: {
      url: "/api/room", // Ganti URL dengan endpoint API untuk room
      dataSrc: "",
    },
    columns: [
      {
        data: null,
        render: function (data, type, row, meta) {
          return meta.row + 1;
        },
      },
      {
        data: "name", // Kolom "Name" sesuai dengan atribut "name" dari model Room
      },
      {
        data: "capacity", // Kolom "Capacity" sesuai dengan atribut "capacity" dari model Room
      },
      {
        data: "location", // Kolom "Location" sesuai dengan atribut "location" dari model Room
      },
      {
        data: "isAvailable",
        render: function (data, type, row, meta) {
          if (data) {
            return '<span class="badge bg-success">Available</span>';
          } else {
            return '<span class="badge bg-danger">Not Available</span>';
          }
        },
      },
      
      {
        data: null,
        render: function (data, type, row, meta) {
          let buttons = `
          `;

          if (isAdmin) {
            buttons += `<div class="d-flex">
            <button class="btn bg-primary bg-opacity-25 text-primary me-2 shadow-sm"
                onclick="editRoom(${row.id})"
                data-bs-toggle="modal" data-bs-target="#createRoom">
                <i class="bi bi-pencil-square"></i>
              </button>
              <button type="button" onclick="deleteRoom(${row.id})"
                      class="btn bg-danger bg-opacity-25 text-danger me-2 shadow-sm">
                      <i class="bi bi-trash"></i>
              </button>
            `;
          }

          buttons += `</div>`;
          return buttons;
        },
      },
    ],
  });
});


function editRoom(id) {
  $.ajax({
    method: "GET",
    url: `/api/room/${id}`, // Ganti URL dengan endpoint API untuk mendapatkan data room berdasarkan ID
    dataType: "JSON",
    success: function (result) {
      $("#roomId").val(result.id);
      $("#name").val(result.name);
      $("#capacity").val(result.capacity);
      $("#location").val(result.location);
      $("#isAvailable").val(result.isAvailable);
      $("#staticBackdropLabel").text("Edit Room"); // Ganti judul modal dengan "Edit Room"
    },
  });
}
function submitForm() {
    const roomId = $("#roomId").val();
    const nameVal = $("#name").val();
    const capacityVal = $("#capacity").val();
    const locationVal = $("#location").val();
    const availableVal = $("#isAvailable").prop('checked'); // Mengambil nilai dari checkbox
    const method = roomId ? "PUT" : "POST"; // Jika roomId ada, gunakan PUT untuk update, jika tidak gunakan POST untuk create.
  
    $.ajax({
      method: method,
      url: roomId ? `/api/room/${roomId}` : "/api/room",
      dataType: "JSON",
      beforeSend: addCsrfToken(),
      data: JSON.stringify({
        name: nameVal,
        capacity: capacityVal,
        location: locationVal,
        isAvailable: availableVal,
      }),
      contentType: "application/json",
      success: function (result) {
        $("#createRoom").modal("hide");
        $("#table-room").DataTable().ajax.reload();
        $("#roomId").val("");
        $("#name").val("");
        $("#capacity").val("");
        $("#location").val("");
        $("#isAvailable").prop('checked', false); // Setelah form disubmit, checkbox direset menjadi unchecked
        Swal.fire({
          position: "center",
          icon: "success",
          title: roomId ? "Room has been updated" : "Room has been created",
          showConfirmButton: false,
          timer: 2000,
        });
      },
    });
  }
  

function deleteRoom(id) {
  // Tampilkan SweetAlert untuk konfirmasi menghapus room
  Swal.fire({
    title: "Are you sure?",
    text: "You won't be able to revert this!",
    icon: "warning",
    showCancelButton: true,
    confirmButtonColor: "#d33",
    cancelButtonColor: "#3085d6",
    confirmButtonText: "Yes, delete it!",
  }).then((result) => {
    if (result.isConfirmed) {
      // Jika pengguna mengklik tombol "Yes", hapus room menggunakan AJAX
      $.ajax({
        url: `/api/room/${id}`, // Ganti URL dengan endpoint API untuk menghapus room berdasarkan ID
        method: "DELETE",
        beforeSend: addCsrfToken(),
        dataType: "JSON",
        success: function (result) {
          $("#table-room").DataTable().ajax.reload();
          Swal.fire({
            position: "center",
            icon: "success",
            title: "Room has been deleted",
            showConfirmButton: false,
            timer: 2000,
          });
        },
      });
    }
  });
}

function detailRoom(id) {
  $.ajax({
    method: "GET",
    url: `/api/room/${id}`, // Ganti URL dengan endpoint API untuk mendapatkan data room berdasarkan ID
    dataType: "JSON",
    success: function (room) {
      $("#detailRoomId").text(room.id);
      $("#detailName").text(room.name);
      $("#detailCapacity").text(room.capacity);
      $("#detailLocation").text(room.location);
      $("#detailisAvailable").text(room.isAvailable);
    },
  });
}
