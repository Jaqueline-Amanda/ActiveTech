<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page contentType="text/html" pageEncoding="iso-8859-1"%>
<jsp:include page="/header.jsp"/>
<jsp:include page="/menu.jsp"/>

<!--Page Heading-->
<div class="row">
    <div class="col-12">
        <div class="page-title-box">
            <h4 class="page-title float-left">Semestres</h4>

            <ol class="breadcrumb float-right">
                <li class="breadcrumb-item">
                    <a href="${pageContext.request.contextPath}/">Home</a>
                </li>
                <li class="breadcrumb-item">
                    <a href="#">Cadastros</a>
                </li>
                <li class="breadcrumb-item">
                    <a href="${pageContext.request.contextPath}/SemestreListar">Semestre</a></li>
                <li class="breadcrumb-item active">Semestres</li>
            </ol>

            <div class="clearfix"></div>
        </div>
    </div>
</div>
                
<!-- DataTables Example -->
<div class="card shadow col-md-12">
    <div class="card-header py-3">
        <h6 class="m-0 font-weight-bold text-primary">Semestres</h6>
    </div>

    <div class="card-body">
       
                <div class="form-row">
                    <input type="hidden" class="form-control" name="idsemestre" id="idsemestre" required value="${semestre.idSemestre}" style="text-transform: uppercase"/>

                    <div class="form-group col-md-8">
                         <label for="numsemestre" class="col-form-label">Semestre</label>
                        <input type="text" class="form-control descricao" id="numsemestre" name="numsemestre" required value="${semestre.numSemestre}" style="text-transform: uppercase">
                    </div>
                </div>
               <tr><td>
                        <input type="hidden" name="situacao" id="situacao" value="${semestre.situacao}" readonly="readonly"/>
                </td></tr>
               <div class="form-group">
                    <button  class="btn btn-success" type="submit"  id="submit" onclick="validarCampos()">Cadastrar</button>
                    <button type="reset" id="limpar"  class="btn btn-danger">Limpar</button>
                    <button class="btn btn-warning">
                       <a href="${pageContext.request.contextPath}/SemestreListar" class="btn-warning">Voltar</a>
                    </button>
               </div>
   </div>
</div>
<script>
   $(document).ready(function () {
	console.log("entrei na ready do documento");
     });
    function validarCampos() {
        console.log("entrei na validação de campos");
        if (document.getElementById("numsemestre").value == '') {
                        Swal.fire({
                                position: 'center',
                                icon: 'error',
                                title: 'Verifique o semestre!',
                                showConfirmButton: false,
                                timer: 1000
                        });
                        $("#numsemestre").focus();
        }else {
                gravarDados();
        }
    }
       
        function gravarDados() {
        console.log("Gravando dados...");
                $.ajax({
                        type: 'post',
                        url: 'SemestreCadastrar',
                        data: {
                                idsemestre: $('#idsemestre').val(),
                                numsemestre: $('#numsemestre').val(),
                                situacao: $('#situacao').val(),
                        },
                        success:
                                function(data) {
                                        console.log("resposta servlet->");
                                        console.log(data);
                                        if (data == 1) {
                                                Swal.fire({
                                                        position: 'center',
                                                        icon: 'success',
                                                        title: 'Sucesso',
                                                        text: 'Semestre gravado com sucesso!',
                                                        showConfirmButton: false,
                                                        timer: 1000
                                                });
                                        } else {
                                                Swal.fire({
                                                        position: 'center',
                                                        icon: 'error',
                                                        title: 'Erro',
                                                        text: 'Não foi possível gravar o semestre!',
                                                        showConfirmButton: false,
                                                        timer: 1000
                                                });
                                        }
                                        setTimeout(() => {window.location.href = "${pageContext.request.contextPath}/SemestreListar"}, 2000);
                                },
                        error:
                                function (data) {
                                        setTimeout(() => {window.location.href = "${pageContext.request.contextPath}/SemestreListar"}, 2000);
                                }
        });
    }
</script>
<%@ include file="/footer.jsp" %>       