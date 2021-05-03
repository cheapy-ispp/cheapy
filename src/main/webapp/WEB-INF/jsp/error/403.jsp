<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="cheapy" tagdir="/WEB-INF/tags" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<link href='https://fonts.googleapis.com/css?family=Lobster' rel='stylesheet'>

<cheapy:layout pageName="Acceso Denegado">
	<h2 class="titulo" style="font-family: 'Lobster'; font-size: 30px; padding:30px">No dispone de las credenciales necesarias para poder acceder a esta página.</h2>
    
    <spring:url value="/resources/images/Logo Cheapy.png" htmlEscape="true" var="cheapyImage"/>
    <img class="img-responsive" src="${fn:escapeXml(cheapyImage)}"/>

    <p>${fn:escapeXml(exception.message)}</p>
	
</cheapy:layout>
