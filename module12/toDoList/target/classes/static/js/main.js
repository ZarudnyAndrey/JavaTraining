var taskId = null;

function changeTask(id) {
    $('#change-task-form').css('display','flex');
    taskId = id;
}

//update
function saveChangeTask() {
  var form = $('#form-task-change');
  var nameVal = form.find('input[name="changeName"]').val();
  var dateVal = form.find('input[name="changeDate"]').val();
  let jsonString = JSON.stringify({id : taskId, name : nameVal, date : dateVal});
  var url = '/tasks/' + taskId;
  $.ajax({
    method: "PUT",
    url: url,
    data: jsonString,
    contentType: 'application/json',
    success: function(response) {
      var oldName = $('.record-' + taskId + ' .task-link');
      var oldDate = $('.record-' + taskId + ' .desc-id-' + taskId);
      oldName.text(nameVal);
      oldDate.text(dateVal);
    },
    error: function() {
      alert('error updating task');
    }
  });
  return false;
}

$(function() {
  const appendTask = function(data) {
    var taskCode = '<a href="#" class="task-link" data-id="' + data.id + '">' + data.name + '</a> ' +
      '<a href="#" class="task-change" data-id="' + data.id + '" onClick="changeTask(' + data.id + ')">change</a> ' +
      '<a href="#" class="task-delete" data-id="' + data.id + '">delete</a>';
    $('#task-list').append('<div class="record-' + data.id + '">' + taskCode + '</div>');
  };

  //read all
//  $.get('/tasks/', function(response) {
//    for(i in response) {
//      appendTask(response[i]);
//    }
//  });

  //read
  $(document).on('click', '.task-link', function() {
    var link = $(this);
    var taskId = $(this).data('id');
    $.ajax({
      method: "GET",
      url: '/tasks/' + taskId,
      success: function(response) {
        var code = '<span class="desc-id-' + taskId + '">Дата: ' + response.date + '</span>';
        link.parent().append(code);
      },
      error: function(response) {
        if(response.status == 404) {
          alert('Задание не найдено!');
        }
      }
    });
    return false;
  });

  //delete
  $(document).on('click', '.task-delete', function() {
    var link = $(this);
    var taskId = $(this).data('id');
    $.ajax({
      method: "DELETE",
      url: '/tasks/' + taskId,
      success: function(response) {
        link.parent().remove();
      },
      error: function(response) {
        if(response.status == 404) {
          alert('Задание не найдено!');
        }
      }
    });
    return false;
  });

  //open form for create
  $('#show-add-task-form').click(function() {
    $('#add-task-form').css('display', 'flex');
  });

  //close form for create
  $('#add-task-form').click(function(event) {
    if(event.target === this) {
      $(this).css('display', 'none');
    }
  });

  //create
  $('#save-task-btn').click(function() {
    var data = $('#add-task-form form').serialize();
    $.ajax({
      method: "POST",
      url: '/tasks/',
      data: data,
      success: function(response) {
        $('#add-task-form').css('display', 'none');
        var task = {};
        task.id = response;
        var dataArray = $('#add-task-form form').serializeArray();
        for(i in dataArray) {
          task[dataArray[i]['name']] = dataArray[i]['value'];
        }
        appendTask(task);
      }
    });
    return false;
  });

  //open form for update
  $(document).on('click', '.task-change', function() {
    $('#change-task-form').css('display', 'block');
  });

  //close form for update
  $('#change-task-form').click(function(event) {
    if(event.target === this) {
      $(this).css('display', 'none');
    }
  });
});