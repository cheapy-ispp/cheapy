<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="cheapy" tagdir="/WEB-INF/tags" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<link href='https://fonts.googleapis.com/css?family=Lobster' rel='stylesheet'>

<cheapy:layout pageName="reviewsClientN">
    <h2 class="titulo" style="font-family: 'Lobster'; text-align:center; font-size:200%;   padding:10px">
    	<c:if test="${review['new']}">Nueva </c:if> Reseña
    </h2>
    <form:form modelAttribute="reviewClient" class="form-horizontal" id="add-review-form">
        <div class="form-group has-feedback">
        	<form:hidden path="id"/>
            <cheapy:textAreaField label="Opinión" name="opinion"/>
            <cheapy:ratingStar label="Servicio" name="service" disabled="false" ></cheapy:ratingStar>
            <cheapy:ratingStar label="Comida" name="food" disabled="false"></cheapy:ratingStar>
            <cheapy:ratingStar label="Calidad/Precio" name="qualityPrice" disabled="false"></cheapy:ratingStar>
        </div>
        <div class="btn-menu">
        	<div class="btns-edit">
                <c:choose>
                    <c:when test="${reviewClient['new']}">
                        <button class="btn btn-default" type="submit" style="font-family: 'Lobster'; font-size: 20px;">
	                        <span class="glyphicon glyphicon-floppy-save" aria-hidden="true" style="padding: 5px"> </span>
	                        Crear reseña</button>
	                        
                    </c:when>
                    <c:otherwise>
                        <button class="btn btn-default" type="submit" style="font-family: 'Lobster'; font-size: 20px;">
                        <span class="glyphicon glyphicon-floppy-save" aria-hidden="true" style="padding: 5px"> </span>
                        Modificar Reseña</button>
                        
                    </c:otherwise>
                </c:choose>
        	</div>
        	<button id='volver' type="button" onclick="history.back()" name="volver atrás" value="volver atrás" style="font-family: 'Lobster';">Volver</button>
        </div>
        
        
	
    </form:form>
</cheapy:layout>
