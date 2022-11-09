const { createApp } = Vue
createApp({
    data() {
        return {
            client: {},
            accounts: [],
            accountType: "",
            loans: [],
            totalBalance:0,
            email: "",
            debitCards: [],
            creditCards: [],
            //formateadorFecha: new Intl.DateTimeFormat('es-MX', { dateStyle: 'medium', timeStyle: 'medium' }),
            moneyFormat: new Intl.NumberFormat('en-US', { style: 'currency', currency: 'USD' }),
            formateadorFecha: new Intl.DateTimeFormat('es-MX', {month:'numeric', year:'2-digit'})
            }},
    created() {
        this.loadData()

    },
    methods: {
    loadData(){
        axios.get('/api/clients/current').then(response =>{
            this.client = response.data
            this.client.accounts.forEach(account => this.totalBalance = this.totalBalance + account.balance)
            this.loans = this.client.loans
            this.cards = this.client.cards
            this.debitCards = this.cards.filter(card => card.type == 'DEBIT')
            this.creditCards = this.cards.filter(card => card.type == 'CREDIT')
            this.email = this.client.email
            this.accounts = this.client.accounts
        })
    },
    logOut() {
        axios.post('/api/logout').then(response =>
            window.location.href = "/public/index.html"
        )
    },
    addAccount(){
        axios.post('/api/clients/current/accounts?' + "email=" + this.email + "&type=" + this.accountType)
        .then(response => window.location.reload())
        .catch(error => console.log(error))
    },
    borrarCuenta(account){
                    axios.patch('/api/accounts/delete?' + "accountNumber=" + account.number)
                        .then(window.location.reload())
    },   
    addNewAccount() {
        let swalWithBootstrapButtons = Swal.mixin({
            customClass: {
                confirmButton: 'btn btn-success',
            },
            buttonsStyling: false
        })
        swalWithBootstrapButtons.fire({
            title: 'Select the type of account',
            icon: 'question',
            confirmButtonText: 'Create account',
            color: 'black',
            reverseButtons: true,
            html:`
                <select name="accountType" id="swal-input1" class="form-select">
                <option class="text-dark" value="" selected>--</option>
                <option class="text-dark" value="SAVING">SAVING</option>
                <option class="text-dark" value="CURRENT">CURRENT</option>
            </select>`,
        }).then((result) => {
            if (result.isConfirmed) {
                this.accountType = document.getElementById('swal-input1').value 
                console.log(this.accountType)
                this.addAccount()
            }
        })
    },

    },
},
).mount('#app')


