<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>  
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
	<head>
	<meta charset="UTF-8">
	<title>Events</title>
	<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
	<link href="/css/style.css" rel="stylesheet" type="text/css"/>
</head>
<body>

	<div class="container">
		<div class="header">
			<h1 class="display-4 logged-in">Welcome, <c:out value="${user.first_name}"/></h1>
			<a href="/logout">Logout</a>
		</div>
		<h4>Here are some of the events in your state:</h4>
		<table class="table">
		  <thead class="thead-dark">
		    <tr>
		      <th scope="col">Name</th>
		      <th scope="col">Date</th>
		      <th scope="col">Location</th>
		      <th scope="col">Host</th>
		       <th scope="col">Action/Status</th>
		    </tr>
		  </thead>
		  <tbody>
			  <c:forEach var="event" items="${eventsIn }">
			    <tr>
			      <th><a href="/events/${event.id}"><c:out value="${event.name}"/></a></th>
			      <td><fmt:formatDate pattern ="MMMM dd, yyyy" value ="${event.date}"/></td>
			      <td><c:out value="${event.city}"/></td>
			      <td><c:out value="${event.host.first_name}"/></td>
			      <td>
		      		<c:choose>
		      			<c:when test="${event.host.id==user.id }">
		      				<a href="/events/${event.id }/edit">Edit</a>  
		      				<a href="/events/${event.id }/delete">Delete</a>
		      			</c:when>
		      			<c:otherwise>
		      				<c:choose>
		      					<c:when test="${event.attendees.contains(user) }">

		      						<span>Joined   <a href="/events/${event.id}/cancel">Cancel</a></span>
		      					</c:when>
		      					<c:otherwise>
		      						<a href="/events/${event.id }/join">Join</a>
		      					</c:otherwise>
		      				</c:choose>
		      			</c:otherwise>
		      		</c:choose>
				  </td>
			    </tr>
	    	  </c:forEach>
		  </tbody>
		</table>
		<h4>Here are some of the events in other states:</h4>
		<table class="table">
		  <thead class="thead-dark">
		    <tr>
		      <th scope="col">Name</th>
		      <th scope="col">Date</th>
		      <th scope="col">Location</th>
		      <th scope="col">Host</th>
		       <th scope="col">Action/Status</th>
		    </tr>
		  </thead>
		  <tbody>
			  <c:forEach var="e" items="${eventsOut }">
			    <tr>
			      <th><a href="/events/${e.id}"><c:out value="${e.name}"/></a></th>
			      <td><fmt:formatDate pattern ="MMMM dd, yyyy" value ="${e.date}"/></td>
			      <td><c:out value="${e.city}"/></td>
			      <td><c:out value="${e.host.first_name}"/></td>
			      <td>
			      	<c:choose>
		      			<c:when test="${e.host.id==user.id }">
		      				<a href="/events/${e.id }/edit">Edit</a>
		      				<a href="/events/${e.id }/delete">Delete</a>
		      			</c:when>
		      			<c:otherwise>
		      				<c:choose>
		      					<c:when test="${e.attendees.contains(user)  }">
		      						<span>Joined   <a href="/events/${event.id}/cancel">Cancel</a></span>
		      					</c:when>
		      					<c:otherwise>
		      						<a href="/events/${e.id }/join">Join</a>
		      					</c:otherwise>
		      				</c:choose>
		      			</c:otherwise>
		      		</c:choose>
			      </td>
			    </tr>
	    	  </c:forEach>
		  </tbody>
		</table>
		<div class="createEvent">
			<h3>Create an Event</h3>
			<p><form:errors path="event.*"/></p>
			<form:form method="POST" action="/events/create" modelAttribute="event">
			<form:hidden path="host" value="${user.id}"/>
	            <form:errors path="name"/>
			<p>
	            <form:label class="col-sm-2 col-form-label" path="name">Event Name:</form:label>
	            <form:input class="form-control col-sm-6" type="text" path="name"/>
	        </p>
	        <p>
	        	<form:label class="col-sm-2 col-form-label" path="date">Date:</form:label>
	        	<form:input class="form-control col-sm-6" type="date" path="date"/>
	        <p>
	            <form:label class="col-sm-2 col-form-label" path="city">Location:</form:label>
	            <form:input class="form-control col-sm-6" type="text" path="city"/>
	        </p>
	        <p>
	            <form:label class="col-sm-2 col-form-label" path="state">State:</form:label>
	            <form:select path="state">
        			<c:forEach items="${states}" var="state">
            		<form:option value="${state}">${state}</form:option>
        			</c:forEach>
        		</form:select>
	        </p>
	        <input class="btn btn-warning" type="submit" value="Create"/>
	        </form:form>
		</div>
	
	</div>


</body>
</html>