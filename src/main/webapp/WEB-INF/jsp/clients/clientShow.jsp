<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="cheapy" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<link href='https://fonts.googleapis.com/css?family=Lobster' rel='stylesheet'>

<cheapy:layout pageName="miPerfil">

    <h2 style="font-family: 'Lobster'; text-align:center; font-size:200%;  color: rgb(0, 64, 128); padding:10px; text-transform: uppercase;"><c:out value="${client.name}"/></h2>


	
    <table class="table table-striped" id="clientTable">
    	<thead>
        <tr>
            <th><fmt:message key="clientInit"/></th>
            <td><c:out value="${client.init}h"/></td>
        </tr>
        <tr>
            <th><fmt:message key="clientFinish"/></th>
            <td><c:out value="${client.finish}h"/></td>
        </tr>
		<tr>
            <th><fmt:message key="name"/></th>
            <td><c:out value="${client.name}"/></td>
        </tr>
        <tr>
            <th><fmt:message key="email"/></th>
            <td><c:out value="${client.email}"/> </td>
        </tr>
        
        <tr>
            <th><fmt:message key="addressClient"/></th>
            <td><c:out value="${client.address}"/> </td>
        </tr><tr>
        <tr>
            <th><fmt:message key="municipio"/></th>
            <td><c:out value="${client.municipio}"/> </td>
        </tr><tr>
            <th><fmt:message key="telephone"/></th>
            <td><c:out value="${client.telephone}"/> </td>
        </tr><tr>
            <th><fmt:message key="descriptionClient"/></th>
            <td><c:out value="${client.description}"/> </td>
        </tr><tr>
            <th><fmt:message key="foodClient"/></th>
            <td><c:out value="${client.food}"/> </td>
        </tr>
        
        
        
        </thead>
    </table>

    <div class="btn-menu" style="float:right">
	    
	<sec:authorize access="hasAnyAuthority('client')">
	<sec:authentication var="principal" property="principal" />
      <div class="btns-edit" style="float:left">
      		
		    <spring:url value="edit" var="editUrl"/>
		    <button type="button" role="link" onclick="window.location='${fn:escapeXml(editUrl)}'" style="font-family: 'Lobster'; font-size: 20px;width: auto;">
		 	    <span class="glyphicon 	glyphicon glyphicon-edit" aria-hidden="true" style="padding: 5px"> </span>
		        Editar cliente</button>
		

	        <spring:url value="disable" var="disableUrl"/>
	        <button type="button" role="link" onclick="window.location='${fn:escapeXml(disableUrl)}'" style="font-family: 'Lobster'; font-size: 20px;width: auto;">
	            <span class="glyphicon 	glyphicon glyphicon-edit" aria-hidden="true" style="padding: 5px"> </span>
	          Desactivar cuenta</button>
	     </div>  
	     
	     <div class="eliminar">   
	        <spring:url value="delete" var="deleteUrl"/>
	        <button type="button" role="link" onclick="window.location='${fn:escapeXml(deleteUrl)}'" >
	            <span class="glyphicon 	glyphicon glyphicon-edit" aria-hidden="true" style="padding: 5px"> </span>
	          Eliminar cuenta</button>
      	</div>
      </sec:authorize>
      <sec:authorize access="hasAnyAuthority('admin')">
		<sec:authentication var="principal" property="principal" />
    	<div class="btns-edit">
		
	        <c:if test="${ client.usuar.enabled eq true}">
	        <spring:url value="/administrators/clients/{username}/disable" var="deactivateUrl">
	        	<spring:param name="username" value="${client.usuar.username}"/>
	        </spring:url>
	        <button type="button" role="link" onclick="window.location='${fn:escapeXml(deactivateUrl)}'" style="font-family: 'Lobster'; font-size: 20px;">
	        	<span class="glyphicon glyphicon glyphicon-trash" aria-hidden="true" style="padding: 5px"> </span>
	         Desactivar cliente</button>
    	 </c:if>

    	 		<c:if test="${ client.usuar.enabled eq false}">
	        <spring:url value="/administrators/clients/{username}/activate" var="activateUrl">
	        	<spring:param name="username" value="${client.usuar.username}"/>
	        </spring:url>
	        <button type="button" role="link" onclick="window.location='${fn:escapeXml(activateUrl)}'" style="font-family: 'Lobster'; font-size: 20px;">
	        	<span class="glyphicon glyphicon glyphicon-trash" aria-hidden="true" style="padding: 5px"> </span>
	         Activar cliente</button>
    	 </c:if>
    	</div>
    	<div class="eliminar">   
	        <spring:url value="/administrators/clients/{clientId}/delete" var="deleteUrl">
	        	<spring:param name="clientId" value="${client.id}"/>
	        </spring:url>
	        <button type="button" role="link" onclick="window.location='${fn:escapeXml(deleteUrl)}'" >
	            <span class="glyphicon 	glyphicon glyphicon-edit" aria-hidden="true" style="padding: 5px"> </span>
	          Eliminar cuenta</button>
      	</div>
    </sec:authorize>
    </div>
  	

</cheapy:layout>
