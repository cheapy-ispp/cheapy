<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="cheapy" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page contentType="text/html; charset=UTF-8" %>

<cheapy:layout pageName="payment">
		<div class="text-center">
			<p class="infoPago"> ¡Enhorabuena! Usted ya tiene la suscripción de un mes para poder utilizar cheapy como bar o restaurante. Puede empezar a crear una oferta pinchando en el siguiente botón:</p>
			<div class="btn-home">
                <button type="button" role="link" onclick="window.location='/offersCreate'" style="font-family: 'Lobster'; font-size: 20px;margin:5px;" class="btn-block">
                <span class="glyphicon glyphicon-cutlery" aria-hidden="true" style="padding: 5px"> </span>
                <fmt:message key="createOffers"/> </button>
            </div>
 				
			
		
		</div>
</cheapy:layout>