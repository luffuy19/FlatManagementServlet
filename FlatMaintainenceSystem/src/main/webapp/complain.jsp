<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
<link
	href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css"
	rel="stylesheet">
<style>
body {
	font-family: 'Arial', sans-serif;
	background-color: #f8f9fa;
}

h2 {
	background-color: #f8f9fa;
	color: #343a40;
	text-align: center;
}

.sidebar {
	position: fixed;
	top: 0;
	bottom: 0;
	left: 0;
	width: 280px;
	padding: 15px;
	background-color: #343a40;
	color: white;
}

.sidebar a {
	color: white;
	text-decoration: none;
}

.sidebar a:hover {
	color: #ffc107;
}

.content {
	margin-left: 280px;
	padding: 15px;
}

.sidebar .nav-item {
	margin-bottom: 0.5rem;
}

.nav-item {
	display: flex;
	align-items: center;
}

.nav-item img {
	margin-right: 10px;
}

.custom-file-input-wrapper {
	position: relative;
	width: 100%;
}

.custom-file-input {
	position: relative;
	z-index: 2;
	width: 100%;
	height: 40px;
	margin: 0;
	opacity: 0;
}

.custom-file-label {
	position: absolute;
	top: 0;
	left: 0;
	right: 0;
	bottom: 0;
	padding: 0.375rem 0.75rem;
	font-weight: 400;
	line-height: 1.5;
	color: #495057;
	background-color: #fff;
	border: 1px solid #ced4da;
	border-radius: 0.25rem;
	transition: border-color 0.15s ease-in-out, box-shadow 0.15s ease-in-out;
	display: flex;
	align-items: center;
}

.custom-file-input:focus ~ .custom-file-label {
	border-color: #80bdff;
	box-shadow: 0 0 0 0.2rem rgba(0, 123, 255, 0.25);
}

.custom-file-input-wrapper .invalid-feedback {
	position: absolute;
	bottom: -1.5rem;
	left: 0;
	width: 100%;
	display: none;
	color: #dc3545;
}

.was-validated .custom-file-input:invalid ~ .custom-file-label {
	border-color: #dc3545;
}

.was-validated .custom-file-input:invalid ~ .custom-file-label ~
	.invalid-feedback {
	display: block;
}

.corner-toggle {
	position: fixed;
	top: 10px;
	right: 10px;
	z-index: 9999;
}

.corner {
	position: fixed;
	top: 70px;
	right: 270px;
	width: 700px;
	display: none;
	z-index: 9999;
}

.corner h4 {
	margin-bottom: 1rem;
}

.corner-btn {
	margin-bottom: 0.5rem;
}

.employee-table {
	margin-top: 1rem;
}
</style>
</head>
<body>
	<div class="sidebar">
		<img style="padding-bottom: 30px;" width="230" height="150"
			src="./img/logo.png" alt=""> <br>
		<ul class="nav flex-column">
			<li class="nav-item"><img width="30" height="30"
				src="./img/addicon.png" alt="Add Tenant" /> <a class="nav-link"
				href="#" data-target="addTenant">Complains</a></li>
			<li class="nav-item"><img width="30" height="30"
				src="./img/back.png" alt="Logout" /> <a class="nav-link" href="#"
				data-target="logout">Back</a></li>
		</ul>
	</div>
	<div class="content">
		<div class="container-fluid">
			<div class="container mt-5">
				<h2 class="mb-4">Complaint Management</h2>
				<table class="table table-bordered">
					<thead class="thead-dark">
						<tr>
							<th>ID</th>
							<th>Complain Type</th>
							<th>Comments</th>
							<th>Complain Date</th>
							<th>Status</th>
							<th>Floor No</th>
							<th>Door No</th>
							<th>Actions</th>
						</tr>
					</thead>
					<tbody id="complaintTableBody">
						<!-- Complaint rows will be dynamically added here -->
					</tbody>
				</table>
				<!-- Modal for assigning worker -->
				<div class="modal fade" id="assignWorkerModal" tabindex="-1"
					aria-labelledby="assignWorkerModalLabel" aria-hidden="true">
					<div class="modal-dialog">
						<div class="modal-content">
							<div class="modal-header">
								<h5 class="modal-title" id="assignWorkerModalLabel">Assign
									Worker</h5>
								<button type="button" class="close" data-dismiss="modal"
									aria-label="Close">
									<span aria-hidden="true">&times;</span>
								</button>
							</div>
							<div class="modal-body">
								<form id="assignWorkerForm">
									<div class="form-group">
										<label for="workerSelect">Select Worker</label> <select
											class="form-control" id="workerSelect" required>
											<option value="" disabled selected>Select a worker</option>
											<option value="Worker1">Worker 1</option>
											<option value="Worker2">Worker 2</option>
											<option value="Worker3">Worker 3</option>
											<!-- Add more workers as needed -->
										</select>
									</div>
									<input type="hidden" id="currentComplaintId">
									<button type="submit" class="btn btn-primary">Assign</button>
								</form>
							</div>
						</div>
					</div>
				</div>
			</div>

			<!-- Button to toggle employee table -->
			<button class="btn btn-secondary btn-sm corner-toggle"
				onclick="toggleEmployeeTable()">
				<i class="fas fa-users"></i> Manage Employees
			</button>

			<!-- Employee Management Section -->
			<div class="corner bg-light border rounded" id="employeeSection">
				<div class="container p-3">
					<h4 class="mb-3">Employee Management</h4>
					<button class="btn btn-primary btn-sm corner-btn"
						onclick="showAddEmployeeModal()">Add Employee</button>
					<div class="employee-table">
						<table class="table table-sm">
							<thead class="thead-dark">
								<tr>
									<th>ID</th>
									<th>Name</th>
									<th>Phone</th>
									<th>Email</th>
									<th>Dept</th>
									<th>Actions</th>
								</tr>
							</thead>
							<tbody id="employeeTableBody">
								<!-- Employee rows will be dynamically added here -->
							</tbody>
						</table>
					</div>
				</div>
			</div>

			<!-- Modal for adding employee -->
			<div class="modal fade" id="addEmployeeModal" tabindex="-1"
				aria-labelledby="addEmployeeModalLabel" aria-hidden="true">
				<div class="modal-dialog">
					<div class="modal-content">
						<div class="modal-header">
							<h5 class="modal-title" id="addEmployeeModalLabel">Add
								Employee</h5>
							<button type="button" class="close" data-dismiss="modal"
								aria-label="Close">
								<span aria-hidden="true">&times;</span>
							</button>
						</div>
						<div class="modal-body">
							<form id="addEmployeeForm">
								<div class="form-group">
									<label for="employeeId">ID</label> <input type="number"
										class="form-control" id="employeeId" required>
								</div>
								<div class="form-group">
									<label for="employeeName">Name</label> <input type="text"
										class="form-control" id="employeeName" required>
								</div>
								<div class="form-group">
									<label for="employeePhone">Phone</label> <input type="text"
										class="form-control" id="employeePhone" required>
								</div>
								<div class="form-group">
									<label for="employeeEmail">Email</label> <input type="email"
										class="form-control" id="employeeEmail" required>
								</div>
								<div class="form-group">
									<label for="employeeDepartment">Department</label> <input
										type="text" class="form-control" id="employeeDepartment"
										required>
								</div>
								<button type="submit" class="btn btn-primary">Add</button>
							</form>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<!-- Scripts -->
	<script src="https://kit.fontawesome.com/a076d05399.js"></script>
	<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
	<script
		src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.2/dist/umd/popper.min.js"></script>
	<script
		src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
	<script>
    // Example complaints data
    const complaints = [
        { id: 1, complain_type: "Plumbing", comments: "Leak in bathroom", complain_date: "2024-06-10", status: "Pending", floor_no: 1, room_no: "A101" },
        { id: 2, complain_type: "Electrical", comments: "Power outage", complain_date: "2024-06-11", status: "In Progress", floor_no: 2, room_no: "B202" },
        { id: 3, complain_type: "HVAC", comments: "AC not working", complain_date: "2024-06-09", status: "Pending", floor_no: 3, room_no: "C303" },
    ];

    // Example employees data
    const employees = [
        { id: 1, name: "John Doe", phone: "123-456-7890", email: "john@example.com", department: "Maintenance" },
        { id: 2, name: "Jane Smith", phone: "987-654-3210", email: "jane@example.com", department: "Electrical" },
        { id: 3, name: "Sam Wilson", phone: "555-555-5555", email: "sam@example.com", department: "Plumbing" },
    ];

    // Function to populate complaint table
    function populateComplaintTable() {
        const complaintTableBody = document.getElementById('complaintTableBody');
        complaintTableBody.innerHTML = '';
        complaints.forEach(complaint => {
            const row = document.createElement('tr');
            row.innerHTML = `
                <td>${complaint.id}</td>
                <td>${complaint.complain_type}</td>
                <td>${complaint.comments}</td>
                <td>${complaint.complain_date}</td>
                <td>${complaint.status}</td>
                <td>${complaint.floor_no}</td>
                <td>${complaint.room_no}</td>
                <td>
                    <button class="btn btn-primary btn-sm" onclick="showAssignWorkerModal(${complaint.id})">Assign Worker</button>
                    <button class="btn btn-success btn-sm" onclick="updateComplaintStatus(${complaint.id}, 'Completed')">Mark as Completed</button>
                </td>
            `;
            complaintTableBody.appendChild(row);
        });
    }

    // Function to show the modal to assign a worker
    function showAssignWorkerModal(complaintId) {
        document.getElementById('currentComplaintId').value = complaintId;
        $('#assignWorkerModal').modal('show');
    }

    // Function to assign a worker to a complaint
    document.getElementById('assignWorkerForm').addEventListener('submit', function(event) {
        event.preventDefault();
        const worker = document.getElementById('workerSelect').value;
        const complaintId = document.getElementById('currentComplaintId').value;
        const complaint = complaints.find(c => c.id == complaintId);
        if (complaint) {
            complaint.worker = worker;
        }
        $('#assignWorkerModal').modal('hide');
        populateComplaintTable();
    });

    // Function to update complaint status
    function updateComplaintStatus(complaintId, status) {
        const complaint = complaints.find(c => c.id == complaintId);
        if (complaint) {
            complaint.status = status;
        }
        populateComplaintTable();
    }

    // Function to populate employee table
    function populateEmployeeTable() {
        const employeeTableBody = document.getElementById('employeeTableBody');
        employeeTableBody.innerHTML = '';
        employees.forEach(employee => {
            const row = document.createElement('tr');
            row.innerHTML = `
                <td>${employee.id}</td>
                <td>${employee.name}</td>
                <td>${employee.phone}</td>
                <td>${employee.email}</td>
                <td>${employee.department}</td>
                <td>
                    <button class="btn btn-danger btn-sm" onclick="deleteEmployee(${employee.id})">Delete</button>
                </td>
            `;
            employeeTableBody.appendChild(row);
        });
    }

    // Function to show the modal to add an employee
    function showAddEmployeeModal() {
        const employeeSection = document.getElementById('employeeSection');
        employeeSection.style.display="none";
        $('#addEmployeeModal').modal('show');
    }

    // Function to add a new employee
    document.getElementById('addEmployeeForm').addEventListener('submit', function(event) {
        event.preventDefault();
        const id = document.getElementById('employeeId').value;
        const name = document.getElementById('employeeName').value;
        const phone = document.getElementById('employeePhone').value;
        const email = document.getElementById('employeeEmail').value;
        const department = document.getElementById('employeeDepartment').value;
        employees.push({ id, name, phone, email, department });
        $('#addEmployeeModal').modal('hide');
        populateEmployeeTable();
    });

    // Function to delete an employee
    function deleteEmployee(employeeId) {
        const index = employees.findIndex(e => e.id == employeeId);
        if (index !== -1) {
            employees.splice(index, 1);
            populateEmployeeTable();
        }
    }

    // Function to toggle employee table visibility
    function toggleEmployeeTable() {
        const employeeSection = document.getElementById('employeeSection');
        if (employeeSection.style.display === "none") {
            employeeSection.style.display = "block";
        } else {
            employeeSection.style.display = "none";
        }
    }

    // Initial population of tables
    populateComplaintTable();
    populateEmployeeTable();
</script>
</body>
</html>