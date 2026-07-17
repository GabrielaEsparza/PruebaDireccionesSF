const API = 'http://localhost:8080/api/direccion'

//cargar estados
async function cargarEstados(){
    const respuesta = await fetch(`${API}/estados`);
    const estados = await respuesta.json();

    const select = document.getElementById('estado');
    estados.forEach(estado =>{
        const opcion = document.createElement('option');
        opcion.value = estado.clave;
        opcion.textContent = estado.nombreEstado;
        select.appendChild(opcion);
    });
}
cargarEstados();

//llenar select generico (lee la clave desde item.id.clave, porque Municipio/Localidad/Colonia tienen llave compuesta)
function llenarSelect(id, items, textoCampo, placeholder) {
    const sel = document.getElementById(id);
    sel.innerHTML = `<option value="">${placeholder}</option>`;
    items.forEach(item => {
        const opcion = document.createElement('option');
        opcion.value = item.id.clave;
        opcion.textContent = item[textoCampo];
        sel.appendChild(opcion);
    });
    sel.disabled = false;
}

//cuando cambia el estado
document.getElementById('estado').addEventListener('change', async function(){
    const clave = this.value;
    if (!clave) return;

    const [munis, locs] = await Promise.all([
        fetch(`${API}/estado/${clave}/municipios`).then(r => r.json()),
        fetch(`${API}/estado/${clave}/localidades`).then(r => r.json())
    ]);

    llenarSelect('municipio', munis, 'descripcion', 'Seleccione...');
    llenarSelect('localidad', locs, 'descripcion', 'Seleccione...');
});

//cuando el CP pierde el foco
document.getElementById('cp').addEventListener('blur', async function(){
    const cp = this.value.trim();
    if (!cp) return;

    try {
        const respuesta = await fetch(`${API}/cp/${cp}`);

        if (!respuesta.ok) {
            document.getElementById('estado').disabled = false;
            document.getElementById('municipio').disabled = true;
            document.getElementById('localidad').disabled = true;
            Swal.fire({ icon: 'error', title: 'CP no encontrado', text: `El código postal ${cp} no existe.`, timer: 3000, timerProgressBar: true });
            return;
        }

        const data = await respuesta.json();

        document.getElementById('estado').value = data.estado;
        document.getElementById('estado').disabled = true;

        const [munis, locs] = await Promise.all([
            fetch(`${API}/estado/${data.estado}/municipios`).then(r => r.json()),
            fetch(`${API}/estado/${data.estado}/localidades`).then(r => r.json())
        ]);

        llenarSelect('municipio', munis, 'descripcion', 'Seleccione...');
        llenarSelect('localidad', locs, 'descripcion', 'Seleccione...');

        document.getElementById('municipio').value = data.municipio || '';
        document.getElementById('localidad').value = data.localidad || '';

        // Estado siempre viene determinado por el CP, así que se bloquea.
        // Municipio y localidad solo se bloquean si el CP realmente trae
        // ese dato en el catálogo; si el catálogo no lo tiene (null), se
        // dejan habilitados para que el usuario los elija manualmente.
        // Si el usuario se equivocó de CP, debe corregir el CP (blur()
        // vuelve a disparar esta rutina y repuebla todo desde cero).
        document.getElementById('municipio').disabled = !!data.municipio;
        document.getElementById('localidad').disabled = !!data.localidad;

        // Las colonias
        const colonias = data.colonias;

        const selectColonia = document.getElementById('colonia');
        const inputColonia  = document.getElementById('colonia-manual');

        if (colonias && colonias.length > 0) {
            llenarSelect('colonia', colonias, 'descripcion', 'Seleccione...');
            selectColonia.style.display = '';
            selectColonia.required = true;
            inputColonia.style.display = 'none';
            inputColonia.required = false;
            inputColonia.value = '';
        } else {
            selectColonia.style.display = 'none';
            selectColonia.required = false;
            inputColonia.style.display = '';
            inputColonia.required = true;
            inputColonia.value = '';
        }

    } catch(e) {
        Swal.fire({ icon: 'error', title: 'Error', text: 'Ocurrió un error al buscar el CP.', timer: 3000, timerProgressBar: true });
    }
});

//Enter en el campo CP simula blur
document.getElementById('cp').addEventListener('keydown', function(e){
    if (e.key === 'Enter') this.blur();
});

//limpiar formulario
function limpiarFormulario() {
    document.getElementById('cp').value = '';
    document.getElementById('estado').value = '';
    document.getElementById('estado').disabled = false;
    document.getElementById('municipio').innerHTML = '<option value="">Seleccione el estado</option>';
    document.getElementById('municipio').disabled = true;
    document.getElementById('localidad').innerHTML = '<option value="">Seleccione el estado</option>';
    document.getElementById('localidad').disabled = true;
    document.getElementById('colonia').innerHTML = '<option value="">Ingrese el CP</option>';
    document.getElementById('colonia').disabled = true;
    document.getElementById('colonia').style.display = '';
    document.getElementById('colonia-manual').style.display = 'none';
    document.getElementById('colonia-manual').value = '';
    document.getElementById('calle').value = '';
}

//boton continuar
async function continuar() {
    const cp        = document.getElementById('cp').value.trim();
    const estado    = document.getElementById('estado').value;
    const municipio = document.getElementById('municipio').value;
    const localidad = document.getElementById('localidad').value;

    const selectColonia = document.getElementById('colonia');
    const inputColonia  = document.getElementById('colonia-manual');
    const coloniaEsManual = selectColonia.style.display === 'none';
    const colonia = coloniaEsManual ? inputColonia.value.trim() : selectColonia.value;

    const calle = document.getElementById('calle').value.trim();

    if (!cp || !estado || !municipio || !localidad || !colonia || !calle) {
        Swal.fire({ icon: 'warning', title: 'Campos incompletos', text: 'Todos los campos son requeridos.', timer: 3000, timerProgressBar: true });
        return;
    }

    const tieneNumero = /\d/.test(calle);
    if (!tieneNumero) {
        Swal.fire({ icon: 'warning', title: 'Calle inválida', text: 'Debes incluir el número en la calle. Ej: Av. Juárez 123', timer: 3000, timerProgressBar: true });
        return;
    }

    // Siempre se valida contra el backend, incluso cuando la colonia se
    // captura a mano (CP sin colonias en catálogo): el backend sabe
    // distinguir ese caso y validar lo demás (estado/municipio/localidad/CP).
    try {
        const respuesta = await fetch(`${API}/validar`, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({ cp, estado, municipio, localidad, colonia })
        });

        const resultado = await respuesta.json();

        if (!resultado.valida) {
            Swal.fire({ icon: 'error', title: 'Error', text: resultado.mensaje, timer: 3000, timerProgressBar: true });
            return;
        }

        await Swal.fire({ icon: 'success', title: '¡Éxito!', text: resultado.mensaje, timer: 3000, timerProgressBar: true });
        limpiarFormulario();

    } catch(e) {
        Swal.fire({ icon: 'error', title: 'Error', text: 'Ocurrió un error al validar la dirección.', timer: 3000, timerProgressBar: true });
    }
}