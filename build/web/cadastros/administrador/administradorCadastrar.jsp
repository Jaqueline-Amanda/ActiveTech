<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page contentType="text/html" pageEncoding="iso-8859-1"%>
<jsp:include page="/header.jsp"/>
<jsp:include page="/cadastros/menuLogado.jsp"/>

    <!-- Page Heading -->
    <div class="row">
        <div class="col-12">
            <div class="page-title-box">
                <h4 class="page-title float-left">Administrador</h4>
                <ol class="breadcrumb float-right">
                    <li class="breadcrumb-item">
                        <a href="${pageContext.request.contextPath}/">Home</a>
                    </li>
                    <li class="breadcrumb-item">
                        <a href="${pageContext.request.contextPath}/AdministradorListar">Administradores</a>
                    </li>
                    <li class="breadcrumb-item active">Cadastro</li>
                </ol>
                    <div class="clearfix"></div>
            </div>
        </div>
    </div>
        
    <div class="card shadow md-4">
        <div class="card-header py-3">
            <h6 class="m-0 font-weight-bold text-primary">Cadastro</h6>
        </div>
        <div class="card-body">
                <div class="form-row">
                    <input type="hidden" class="form-control descricao" id="idadministrador" name="idadministrador"
                               required value="${administrador.idAdministrador}">
                    <input type="hidden" class="form-control descricao" id="idpessoa" name="idpessoa"
                               required value="${administrador.idPessoa}">
                    <div class="form-group col-md-4">
                        <label for="nome" class="col-form-label">Nome</label>
                        <input type="text" class="form-control descricao" id="nome" name="nome"
                               required value="${administrador.nome}" style="text-transform: uppercase">
                    </div>
                    <div class="form-group col-md-3">
                        <label for="login" class="col-form-label">Login</label>
                        <input type="text" class="form-control descricao" id="login" name="login"
                               required value="${administrador.login}">
                    </div>
                    <div class="form-group col-md-3">
                        <label for="senha" class="col-form-label">Senha</label>
                        <input type="password" class="form-control descricao" id="senha" name="senha"
                               required value="${administrador.senha}">
                    </div>
                    <div class="form-group col-md-2">
                            <label class="col-form-label">CPF</label>
                            <input class="form-control descricao" type="text" name="cpf" id="cpf" 
                                   value="${administrador.cpf}" />
                    </div>
                    <div class="form-group col-md-1">
                        <label class="col-form-label">Permite Login</label>
                        <select class="form-control descricao" name="permitelogin" id="permitelogin">
                            <option value="N" ${administrador.permiteLogin == 'N' ? "selected" : ""}>N?o</option>
                            <option value="S" ${administrador.permiteLogin == 'S' ? "selected" : ""}>Sim</option>       
                        </select>    
                    </div>
                </div>
                <div class="form-group">        
                    <button type="submit" class="btn btn-success navbar-btn" id="submit" onclick="validarCampos()"><i class="fa fa-floppy-o" aria-hidden="true"></i> Salvar </button>
                    <button type="reset" class="btn btn-danger navbar-btn" id="reset" onclick="limparCampos()"><i class="fa fa-floppy-o" aria-hidden="true"></i> Limpar</button>
                    <button class="btn btn-warning navbar-btn">
                        <a href="${pageContext.request.contextPath}/AdministradorListar" class="btn-warning" style="text-decoration: none">Cancelar</a>
                    </button>   
                </div>
        </div>
    </div>

<script>
function validarCampos() {
    console.log("entrei na valida??o de campos");
    if (document.getElementById("nome").value == '') {
        Swal.fire({
            position: 'center',
            icon: 'error',
            title: 'Verifique o nome do administrador!',
            showConfirmButton: false,
            timer: 1000
        });
        $("#nome").focus();
    } else if (document.getElementById("senha").value == '') {
        Swal.fire({
            position: 'center',
            icon: 'error',
            title: 'Verifique a senha do administrador!',
            showConfirmButton: false,
            timer: 1000
        });
        $("#senha").focus();
    } else if (document.getElementById("login").value == '') {
        Swal.fire({
            position: 'center',
            icon: 'error',
            title: 'Verifique o login do administrador!',
            showConfirmButton: false,
            timer: 1000
        });              
        $("#login").focus();
    } else if (document.getElementById("cpf").value == '') {
        Swal.fire({
            position: 'center',
            icon: 'error',
            title: 'Verifique o CPF do administrador!',
            showConfirmButton: false,
            timer: 1000
        });              
    } else {
        gravarDados();
    }
}

function gravarDados() {
    console.log("Gravando dados...");
    $.ajax({
        type: 'post',
        url: 'AdministradorCadastrar',
        data: {
            idadministrador: $('#idadministrador').val(),
            idpessoa: $('#idpessoa').val(),
            senha: $('#senha').val(),
            nome: $('#nome').val(),
            cpf: $('#cpf').unmask().val(),
            login: $('#login').val(),
            permitelogin: $('#permitelogin').val()
        },
        success:
                function (data) {
                    console.log("resposta servlet ->");
                    console.log(data);
                    if (data == 1) {
                        Swal.fire({
                            position: 'center',
                            icon: 'success',
                            title: 'Sucesso',
                            text: 'Administrador cadastrado com sucesso!',
                            showConfirmButton: false,
                            timer: 1000
                        });
                    } else {
                        Swal.fire({
                            position: 'center',
                            icon: 'error',
                            title: 'Erro',
                            text: 'N?o foi poss?vel cadastrar o administrador!',
                            showConfirmButton: false,
                            timer: 1000
                        });
                    }
                    setTimeout(() => {window.location.href = "${pageContext.request.contextPath}/AdministradorListar"}, 1000);
                },
        error:
                function (data) {
                    setTimeout(() => {window.location.href = "${pageContext.request.contextPath}/AdministradorListar"}, 1000);
                }
    });
}
    
function limparCampos() {
    $('#idadministrador').val("");
    $('#permitelogin').val("");
    $('#situacao').val("");
    $('#idpessoa').val("");
    $('#nome').val("");
    $('#login').val("");
    $('#senha').val("");
     $('#cpf').val("");
}
    
/*function carregarPessoa(v) {
    var idM = v;
    var tipoPessoa = 'administrador';
    //console.log("Usuario = " + idM);
    $(document).ready(function () {
        $.getJSON('PessoaBuscarCpf', {cpfcnpjpessoa: idM, tipopessoa: tipoPessoa}, function (respostaAdm) {
            console.log(respostaAdm);
            //var id = respostaAdm.idAdministrador;
            if (respostaAdm != null)
            {
                $('#idadministrador').val(respostaAdm.idAdministrador);
                $('#permitelogin').val(respostaAdm.permiteLogin);
                $('#situacao').val(respostaAdm.situacao);
                $('#idpessoa').val(respostaAdm.idPessoa);
                $('#cpf').val(respostaAdm.cpf);
                $('#nome').val(respostaAdm.nome);
                $('#login').val(respostaAdm.login);
                $('#senha').val(respostaAdm.senha);
            } else {
                //se n?o encontrou administrador busca por pessoa somente
                tipoPessoa = 'pessoa';
                $.getJSON('PessoaBuscarCpf', {cpfcnpjpessoa: idM, tipopessoa: tipoPessoa}, function (respostaPessoa) {
                    console.log(respostaPessoa);
                    var id = respostaPessoa.idPessoa;
                    if (id != "0")
                    {
                        $('#idpessoa').val(respostaPessoa.idPessoa);
                        $('#cpf').val(respostaPessoa.cpf);
                        $('#nome').val(respostaPessoa.nome);
                        $('#senha').val(respostaPessoa.senha);
                        $('#login').val(respostaPessoa.login);
                    }
                });
            }
        });
    });
}*/
    
</script>

<%@ include file="/footer.jsp"%>