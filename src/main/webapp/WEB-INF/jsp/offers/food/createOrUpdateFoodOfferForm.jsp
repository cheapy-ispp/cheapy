<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="cheapy" tagdir="/WEB-INF/tags" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<link href='https://fonts.googleapis.com/css?family=Lobster' rel='stylesheet'>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>

<cheapy:layout pageName="foodOffers">
	<script>
		$(document).ready(function(){
		  $('[data-toggle="desplegable"]').popover();   
		});
	</script>
    <h2 class="titulo" style="font-family: 'Lobster'; font-size:200%; padding:10px">
        <c:if test="${foodOffer['new']}"><fmt:message key="new"/> </c:if> <fmt:message key="foodOffer"/>
    </h2>
    
    <form:form modelAttribute="foodOffer" class="form-horizontal" enctype="multipart/form-data" id="add-foodOffer-form">
        <div class="form-group has-feedback">
        	<form:hidden path="id"/>
            <form:hidden path="code"/>
            <form:hidden path="status"/>
            <cheapy:dateField label="Fecha de Inicio" placeholder="dd/MM/yyyy HH:mm" name="start"/>
            <cheapy:dateField label="Fecha de Fin" placeholder="dd/MM/yyyy HH:mm" name="end"/>
            <cheapy:inputField label="Plato" placeholder="Ej. Macarrones" name="food"/>
            <cheapy:inputField label="Precio Original" placeholder="XX€ (Ej.6)" name="price"/>
            <cheapy:inputField label="Descuento" placeholder="XX% (Ej.15)" name="discount"/>
            <c:set var="cssGroup" value="form-group ${status.error ? 'has-error' : '' }"/>
		    <c:set var="valid" value="${not status.error and not empty status.actualValue}"/>
		    <div class="${cssGroup}">
		        <label class="col-sm-2 control-label">Imagen
			        <a title="Ayuda" data-toggle="desplegable" data-trigger="hover" data-placement="bottom" data-content="Para copiar la url de una imagen en internet debe hacer click derecho sobre esta y seleccionar la opción 'Copiar dirección de la imagen'">
	    			<span class="glyphicon glyphicon-question-sign" aria-hidden="true" style="padding: 5px; color:#325a80 "> </span></a>
		        </label>
				
		        <div class="col-sm-10">
		            <form:input class="form-control" placeholder="Copiar la url de una imagen del plato aquí" path="image" style="width:70%"/>
		            <c:if test="${valid}">
		                <span class="glyphicon glyphicon-ok form-control-feedback" aria-hidden="true"></span>
		            </c:if>
		            <c:if test="${status.error}">
		                <span class="glyphicon glyphicon-remove form-control-feedback" aria-hidden="true"></span>
		                <span class="help-inline">${status.errorMessage}</span>
		            </c:if>
		        </div>
		    </div>
		</div>
        <div class="form-group">
            <div class="col-sm-offset-2 col-sm-10">
            	<div class="btn-mod">
	                <c:choose>
	                    <c:when test="${foodOffer['new']}">
	                        <button class="btn btn-default" type="submit" style="font-family: 'Lobster'; font-size: 20px;">
	                        <span class="glyphicon glyphicon-floppy-save" aria-hidden="true" style="padding: 5px"> </span>
	                        Crear oferta</button>
	                    </c:when>
	                    <c:otherwise>
	                        <button class="btn btn-default" type="submit" style="font-family: 'Lobster'; font-size: 20px;">
	                        <span class="glyphicon glyphicon-floppy-save" aria-hidden="true" style="padding: 5px"> </span>
	                        Modificar</button>
	                    </c:otherwise>
	                </c:choose>
                </div>
            </div>
        </div>
    </form:form>
    
</cheapy:layout>
