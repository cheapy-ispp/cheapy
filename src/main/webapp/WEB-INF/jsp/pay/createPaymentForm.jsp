<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="cheapy" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<cheapy:layout pageName="payment">
	<center>
	
		<c:if test="${payCham==true}">
			<h2>${order.price}${order.currency}</h2>
			<h2>${order.description}</h2>		
		</c:if>
		
		<form:form commandName="order" modelAttribute="order" method="post" action="/pay">
			<div>
				<form:input type="hidden" path="price" />
				<form:input type="hidden" path="currency" />
				<form:input type="hidden" path="method" />
				<form:input type="hidden" path="intent" />
				<form:input type="hidden" path="description" />

				<div class="form-group">
					<button class="boton" value="Pagar" type="submit">
						<img width="146px" height="36px" src="/images/logoPayPal.png"
									alt="PayPalLOGO">
					</button>
				</div>
			</div>
		</form:form>
	</center>
</cheapy:layout>