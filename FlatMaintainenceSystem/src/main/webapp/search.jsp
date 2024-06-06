<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="com.chainsys.model.Tenant"%>
<%@ page import="java.util.*"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Bootstrap Sidebar</title>
<link
	href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css"
	rel="stylesheet">
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css">
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

.tenant-info {
	margin-top: 50px;
}

.tenant-info img {
	max-width: 150px;
	height: auto;
	margin-bottom: 20px;
	border-radius: 4%;
}

.tenant-info h2 {
	margin-bottom: 10px;
	color: #000000;
	font-size: 24px;
}

.info-item {
	margin-bottom: 15px;
	color: #495057;
}

.info-item strong {
	color: #000000;
}

.card {
	border-radius: 10px;
	box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
	transition: box-shadow 0.3s ease-in-out;
}

.card:hover {
	box-shadow: 0 8px 16px rgba(0, 0, 0, 0.2);
}

.card-header {
	background-color: #000000;
	color: white;
	border-radius: 10px 10px 0 0;
	padding: 15px;
}

.card-body {
	background-color: #fff;
	padding: 15px;
}

.tenant-header {
	display: flex;
	align-items: center;
}

.tenant-header img {
	margin-right: 20px;
}
</style>
</head>
<body>
	<div class="sidebar">
		<img style="padding-bottom: 30px;" width="230" height="150"
			src="img/logo.png" alt=""> <br>
		<ul class="nav flex-column">
			<li class="nav-item"><img width="30" height="30"
				src="img/addicon.png" alt="Add Tenant">
				<a class="nav-link" href="#" data-target="addTenant">Search</a></li>
			<li class="nav-item"><img width="30" height="30"
				src="img/logout.png" alt="Logout">
				<a class="nav-link" href="#" data-target="logout">Back</a></li>
		</ul>
	</div>

	<div class="content">
		<div class="container-fluid">
			<div class="row mt-3">
				<div class="col-md-6">
					<form action="SearchTenantServlet" method="get">
						<div class="input-group">
							<div class="input-group-prepend">
								<span class="input-group-text" id="search-addon"><i
									class="fa fa-search"></i></span>
							</div>
							<input type="text" name="query" class="form-control"
								placeholder="Search" aria-label="Search"
								aria-describedby="search-addon" >
						</div>
				</div>
				<div class="col-md-3">
					<div class="input-group">
						<button type="submit" class="btn btn-dark">Search</button>
					</div>
				</div>
				</form>
			</div>
			<div class="container tenant-info">
				<!-- Form for deleting tenants -->
				<form action="DeleteServlet" method="post">
					<!-- Display search results here -->
					<%
					List<Tenant> tenantList = (List<Tenant>) request.getAttribute("tenantList");
					Integer currentPageObj = (Integer) request.getAttribute("currentPage");
					int currentPage = (currentPageObj != null) ? currentPageObj.intValue() : 1;
					Integer totalPagesObj = (Integer) request.getAttribute("totalPages");
					int totalPages = (totalPagesObj != null) ? totalPagesObj.intValue() : 1;
					String query = (String) request.getAttribute("query");

					if (tenantList != null && !tenantList.isEmpty()) {
						for (Tenant tenant : tenantList) {
					%>
					<div class="tenant-header mb-4">
						<img src="data:image/jpeg;base64,<%=Base64.getEncoder().encodeToString(tenant.getPhoto())%>" alt="Tenant Photo" class="img-thumbnail" id="tenantPhoto">
						<div>
							<h2><%=tenant.getName()%></h2>
							<div class="info-item">
								<strong>Phone Number:</strong> <span><%=tenant.getPhoneNo()%></span>
							</div>
						</div>
					</div>
					<div class="row">
						<div class="col-md-12 mb-4">
							<div class="card">
								<div class="card-header">Personal Information</div>
								<div class="card-body">
									<div class="info-item">
										<strong>Aadhaar Number:</strong> <span><%=tenant.getAadhaarNumber()%></span>
									</div>
									<div class="info-item">
										<strong>Family Members:</strong> <span><%=tenant.getFamilyNembers()%></span>
									</div>
									<div class="info-item">
										<strong>Date of Joining:</strong> <span><%=tenant.getDateOfJoining()%></span>
									</div>
								</div>
							</div>
						</div>
						<div class="col-md-12 mb-4">
							<div class="card">
								<div class="card-header">Financial Information</div>
								<div class="card-body">
									<div class="info-item">
										<strong>Advance Amount:</strong> <span><%=tenant.getAdvanceAmount()%></span>
									</div>
									<div class="info-item">
										<strong>Advance Status:</strong> <span><%=tenant.getAdvanceStatus()%></span>
									</div>
									<div class="info-item">
										<strong>Rent Amount:</strong> <span><%=tenant.getRentAmount()%></span>
									</div>
									<div class="info-item">
										<strong>Rent Status:</strong> <span><%=tenant.getRentAmountStatus()%></span>
									</div>
									<div class="info-item">
										<strong>EB Bill:</strong> <span><%=tenant.getEbBill()%></span>
									</div>
									<div class="info-item">
										<strong>EB Bill Status:</strong> <span><%=tenant.getEbBillStatus()%></span>
									</div>
								</div>
							</div>
						</div>
					</div>
					<!-- Delete Button -->
					<div class="mb-4">
							<input type="hidden" value="<%= tenant.getId() %>" name="deleteId">
							<button type="submit" name="deleteTenant" class="btn btn-danger">Delete Account</button>
					</div>
					<%
					}
					} else {
					%>
					<p>No tenants found.</p>
					<%
					}
					%>
				</form>

				<!-- Pagination Controls -->
				<nav aria-label="Page navigation example">
					<ul class="pagination">
						<%
						if (currentPage > 1) {
						%>
						<li class="page-item"><a class="page-link"
							href="SearchTenantServlet?query=<%=query%>&page=<%=currentPage - 1%>"
							aria-label="Previous"> <span aria-hidden="true">&laquo;</span>
						</a></li>
						<%
						}
						for (int i = 1; i <= totalPages; i++) {
						%>
						<li class="page-item <%=(i == currentPage) ? "active" : ""%>">
							<a class="page-link"
							href="SearchTenantServlet?query=<%=query%>&page=<%=i%>"><%=i%></a>
						</li>
						<%
						}
						if (currentPage < totalPages) {
						%>
						<li class="page-item"><a class="page-link"
							href="SearchTenantServlet?query=<%=query%>&page=<%=currentPage + 1%>"
							aria-label="Next"> <span aria-hidden="true">&raquo;</span>
						</a></li>
						<%
						}
						%>
					</ul>
				</nav>
			</div>
		</div>
	</div>


	<!-- Bootstrap JS and dependencies -->
	<script
		src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.6/dist/umd/popper.min.js"></script>
	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.min.js"></script>
	<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
	<script src="https://code.jquery.com/jquery-3.3.1.slim.min.js"></script>
	<script
		src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js"></script>
	<script
		src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"></script>
</body>
</html>