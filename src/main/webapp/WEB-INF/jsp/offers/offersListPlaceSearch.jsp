<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="cheapy" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<link href='https://fonts.googleapis.com/css?family=Lobster' rel='stylesheet'>

<cheapy:layout pageName="busqueda de ofertas">
	<script>
		function pagNum(pagina) {
			paginaAct = pagina + 1;
			document.write("Página " + paginaAct + " <br />");
		}
	

	</script>

    <h2 style="font-family: 'Lobster'; text-align:center; font-size:200%;  color: rgb(0, 64, 128); padding:10px">Busqueda de Ofertas</h2>
	<c:if test="${empty datos }">
		<p id="vacio" >No hay ninguna oferta creada.</p>
	</c:if>
	<c:if test="${not empty datos }">
		<div class="table-responsive">
		    <table id="offerTable" class="table table-striped">
		        <thead>
		        <tr>
		        	<th>Restaurante</th>
		        	<th>Tipo de oferta</th>
		            <th><fmt:message key="startDate"/></th>
		            <th><fmt:message key="endDate"/></th>
		            <th></th>
		        </tr>
		        </thead>
		        <tbody>
		        <c:forEach items="${datos}" var="datos">
		            <tr>
		                <td>
		                    <a href="/restaurant/${datos[0].client.id}"><c:out value="${datos[0].client.name}"/></a>
		                </td>
		                <td>
		                	<c:if test="${datos[1] == 'time'}">
		                    	<c:out value="Por franja horaria"/>
		                    </c:if>
		                    <c:if test="${datos[1] == 'nu'}">
		                    	<c:out value="Por numero de comensales"/>
		                    </c:if>
		                    <c:if test="${datos[1] == 'speed'}">
		                    	<c:out value="Por rapidez"/>
		                    </c:if>
		                    <c:if test="${datos[1] == 'food'}">
		                    	<c:out value="Por plato especifico"/>
		                    </c:if>
		                </td>
		                <td>
		                    <c:out value="${localDateTimeFormat.format(datos[0].start)}"/>
		                </td>
		                <td>
		                    <c:out value="${localDateTimeFormat.format(datos[0].end)}"/>
		                </td>           	                
		                <td>
	                	<spring:url value="/offers/${datos[1]}/${datos[0].id}" var="offerUrl">
	                	</spring:url>
	               		<div class="btn-detalles">
                			<button type="button" role="link" onclick="window.location='${fn:escapeXml(offerUrl)}'" style="font-family: 'Lobster'; font-size: 20px;">
                			<span class="glyphicon glyphicon-info-sign" aria-hidden="true" style="padding: 5px"> </span>
	                		<fmt:message key="details"/></button>
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
	<div style="display:inline">
	<c:if test='${page!=0}'>

    	<spring:url value="/offersByPlace/{page}?municipio={mun}" var="SearchOfferListUrl">
    		<spring:param name="page" value="${page-1}"/>
    		<spring:param name="mun" value="${mun}"/>
    	</spring:url>
    	<button type="button" class="btn-pag" role="link" onclick="window.location='${fn:escapeXml(SearchOfferListUrl)}'" style="font-family: 'Lobster'; font-size: 20px;">
		<span class="glyphicon 	glyphicon glyphicon-arrow-left" aria-hidden="true" style="padding: 5px"> </span>
		Pág. anterior</button>

    </c:if>
    
    <c:if test="${nextPage > 0}">

    	<spring:url value="/offersByPlace/{page}?municipio={mun}" var="SearchOfferListUrl">
    		<spring:param name="page" value="${page+1}"/>
    		<spring:param name="mun" value="${mun}"/>
    	</spring:url>
    	<button type="button" class="btn-pag"  role="link" onclick="window.location='${fn:escapeXml(SearchOfferListUrl)}'" style="font-family: 'Lobster'; font-size: 20px;">
		<span class="glyphicon 	glyphicon glyphicon-arrow-right" aria-hidden="true" style="padding: 5px"> </span>
		Pág. siguiente</button>

	</c:if>
	</div>
    </c:if>
</cheapy:layout>