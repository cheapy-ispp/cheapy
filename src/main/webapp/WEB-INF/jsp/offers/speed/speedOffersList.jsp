<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="cheapy" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page contentType="text/html; charset=UTF-8" %>

<link href='https://fonts.googleapis.com/css?family=Lobster' rel='stylesheet'>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>

<cheapy:layout pageName="ofertas por rapidez">
	<script>
		function pagNum(pagina) {
			paginaAct = pagina + 1;
			document.write("Página " + paginaAct + " <br />");
		}
		$(document).ready(function(){
		  $('[data-toggle="desplegable"]').popover();   
		});
	</script>
	<div class="text-center">
		<div class="btn-filter-max">
			<spring:url value="/offers/foodOfferList/{page}" var="foodOfferListUrl">
				<spring:param name="page" value="0"/>
		    </spring:url>
		    <button type="button" role="link" class="btn-filter" onclick="window.location='${fn:escapeXml(foodOfferListUrl)}'" style="font-family: 'Lobster'; font-size: 20px;">
			<span class="glyphicon 	glyphicon glyphicon-cutlery" aria-hidden="true" style="padding: 5px"> </span>
			Ofertas de plato específico</button>
			
			<spring:url value="/offers/nuOfferList/{page}" var="nuOfferListUrl">
				<spring:param name="page" value="0"/>
		    </spring:url>
		    <button type="button" role="link" class="btn-filter" onclick="window.location='${fn:escapeXml(nuOfferListUrl)}'" style="font-family: 'Lobster'; font-size: 20px;">
			<span class="glyphicon 	glyphicon glyphicon-cutlery" aria-hidden="true" style="padding: 5px"> </span>
			Ofertas por número de comensales</button>
			
			<spring:url value="/offers/speedOfferList/{page}" var="speedOfferListUrl">
				<spring:param name="page" value="0"/>
		    </spring:url>
		    <button type="button" role="link" class="btn-filter-active" onclick="window.location='${fn:escapeXml(speedOfferListUrl)}'" style="font-family: 'Lobster'; font-size: 20px;">
			<span class="glyphicon 	glyphicon glyphicon-cutlery" aria-hidden="true" style="padding: 5px"> </span>
			Ofertas de velocidad</button>
			
			<spring:url value="/offers/timeOfferList/{page}" var="timeOfferListUrl">
				<spring:param name="page" value="0"/>
		    </spring:url>
		    <button type="button" role="link" class="btn-filter" onclick="window.location='${fn:escapeXml(timeOfferListUrl)}'" style="font-family: 'Lobster'; font-size: 20px;">
			<span class="glyphicon 	glyphicon glyphicon-cutlery" aria-hidden="true" style="padding: 5px"> </span>
			Ofertas de franja horaria</button>
		</div>
	</div>
	
	<spring:url value="/offersByName/{page}" var="SearchNameOfferListUrl">
				<spring:param name="page" value="0"/>
	</spring:url>
	<form class="example" action="${fn:escapeXml(SearchNameOfferListUrl)}">
		<h2 class="titulo" style="font-family: 'Lobster'; font-size:150%; padding:10px;" >Búsqueda por nombre del bar/restaurante: </h2>
		<div class="text-center">
	  		<input type="text" placeholder="Búsqueda por nombre" name="name" style="border:solid; width:80%; border-color: rgb(0, 64, 128);">
	  		<button type="submit" class="btn-search">
	  		<span class="glyphicon 	glyphicon glyphicon-search" aria-hidden="true" style="padding: 5px"> </span>
	  		</button>
  		</div>
	</form>
	
	<spring:url value="/offersByFood/{page}" var="SearchFoodOfferListUrl">
				<spring:param name="page" value="0"/>
	</spring:url>
	<form class="example" action="${fn:escapeXml(SearchFoodOfferListUrl)}">
		<h2 class="titulo" style="font-family: 'Lobster'; font-size:150%; padding:10px;" >Búsqueda por tipo de comida: </h2>
		<div class="text-center">
	  		<input type="text" placeholder="Búsqueda por tipo comida (Ej: americana)" name="name" style="border:solid; width:80%; border-color: rgb(0, 64, 128);">
	  		<button type="submit" class="btn-search">
	  		<span class="glyphicon 	glyphicon glyphicon-search" aria-hidden="true" style="padding: 5px"> </span>
	  		</button>
  		</div>
	</form>
	
	<spring:url value="/offersByPlace/{page}" var="SearchPlaceOfferListUrl">
				<spring:param name="page" value="0"/>
	</spring:url>
	<form class="example" action="${fn:escapeXml(SearchPlaceOfferListUrl)}">
		<div class="text-center">
			<select name="municipio" class="select-municipio" >
				<option value="">Seleccione una de las opciones</option>
			
			<c:forEach items="${municipios}" var="entry">
				<option value="${fn:escapeXml(entry)}">${fn:escapeXml(entry.toString())}</option>
			</c:forEach>
			</select>
			<button type="submit" class="btn-mas">Buscar por municipio</button>
		</div>
	</form>
	
	<spring:url value="/offersByDate/{page}" var="SearchDateOfferListUrl">
			<spring:param name="page" value="0"/>
	</spring:url>
	<form class="example" action="${fn:escapeXml(SearchDateOfferListUrl)}">
		<h2 class="titulo" style="font-family: 'Lobster'; font-size:150%; padding:10px;" >Búsqueda por fecha: </h2>
		<div class="text-center">
	  		<input type="datetime-local" class="time"  name="start" style="width:70%"/>
	  		<button type="submit" class="btn-search">
	  		<span class="glyphicon 	glyphicon glyphicon-search" aria-hidden="true" style="padding: 5px"> </span>
	  		</button>
  		</div>
	</form>
	
    <h2 class="titulo" style="font-family: 'Lobster'; font-size:200%; padding:10px"><fmt:message key="speedOffers"/>
    	<a title="Información" data-toggle="desplegable" data-trigger="hover" data-placement="bottom" data-content="Descuento al consumir en menos de alguno de los tres posibles tiempos">
    	<span class="glyphicon glyphicon-question-sign" aria-hidden="true" style="padding: 5px"> </span></a>
    </h2>
	<c:if test="${empty speedOfferLs }">
		<p id="vacio" >No hay ninguna oferta por tiempo empleado en comer activa.</p>
	</c:if>
	<c:if test="${not empty speedOfferLs }">
	<div class="table-responsive">
	    <table id="speedOfferTable" class="table table-striped">
	        <thead>
	        <tr>
	            <th><fmt:message key="name"/></th>
	            <th><fmt:message key="startDate"/></th>
	            <th><fmt:message key="endDate"/></th>
	            <th><fmt:message key="goldGoal"/></th>
	            <th><fmt:message key="goldDiscount"/></th>
	            <th><fmt:message key="municipio"/></th>
	            <th> </th>
	            
	        </tr>
	        </thead>
	        <tbody>
	        <c:forEach items="${speedOfferLs}" var="speedOffer">
	            <tr>
	                <td>
	                    <a href="/restaurant/${fn:escapeXml(speedOffer.client.id)}"><c:out value="${speedOffer.client.name}"/></a>
	                </td>
	                <td>
	                    <c:out value="${localDateTimeFormat.format(speedOffer.start)}"/>
	                </td>
	                <td>
	                    <c:out value="${localDateTimeFormat.format(speedOffer.end)}"/>
	                </td>
	                <td>
	                    <c:out value="${speedOffer.gold.getHour()*60+speedOffer.gold.getMinute()} minutos y"/><c:out value=" ${speedOffer.gold.getSecond()} segundos"/>
	                </td>
	                <td>
	                    <c:out value="${speedOffer.discountGold}%"/>
	                </td>
	                
	                <td>
	                    <c:out value="${speedOffer.client.municipio}"/>
	                </td>
	                
	                <td>
	                    <spring:url value="/offers/speed/{speedOfferId}" var="speedOfferUrl">
	                        <spring:param name="speedOfferId" value="${speedOffer.id}"/>
	                    </spring:url>
	                    <div class="btn-detalles">
		                    <button type="button" role="link" onclick="window.location='${fn:escapeXml(speedOfferUrl)}'" class="btn-detalles" style="font-family: 'Lobster'; font-size: 20px;">
		                    <span class="glyphicon glyphicon-info-sign" aria-hidden="true" style="padding: 5px"> </span>
		                    <fmt:message key="details"/> </button>
		                </div>
	                </td>
	                  
	            </tr>
	        </c:forEach>
	        </tbody>
	    </table>
    </div>
    <div class="text-center">
    	<script type="text/javascript">
			          
    		pagNum(${page});
								
		</script>
    </div>
    <div class="row-pag-btn">
	    <div class="column-pag-btn" style="text-align: left;">
	    	 <c:if test='${page!=0}'>
		    	<spring:url value="/offers/speedOfferList/{page}" var="speedOfferListUrl">
		    		<spring:param name="page" value="${page-1}"/>
		    	</spring:url>
		    	<button type="button" class="btn-pag" role="link" onclick="window.location='${fn:escapeXml(speedOfferListUrl)}'" style="font-family: 'Lobster'; font-size: 20px;">
				<span class="glyphicon 	glyphicon glyphicon-arrow-left" aria-hidden="true" style="padding: 5px"> </span>
				Pág. anterior</button>
	    </c:if>
	    	​
	    </div>
	    <div class="column-pag-btn" style="text-align: right;">
	    	<c:if test="${nextPage > 0}">
		    	<spring:url value="/offers/speedOfferList/{page}" var="speedOfferListUrl">
		    		<spring:param name="page" value="${page+1}"/>
		    	</spring:url>
		    	<button type="button" class="btn-pag" role="link" onclick="window.location='${fn:escapeXml(speedOfferListUrl)}'" style="font-family: 'Lobster'; font-size: 20px;">
				<span class="glyphicon 	glyphicon glyphicon-arrow-right" aria-hidden="true" style="padding: 5px"> </span>
				Pág. siguiente
				</button>
			</c:if>​
		</div>
	</div>
    </c:if>
</cheapy:layout>
