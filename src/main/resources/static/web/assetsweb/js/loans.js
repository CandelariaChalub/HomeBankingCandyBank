const app = Vue.
createApp({
    data() {
        return {
            loans: [],
            client: {},
            accounts: [],
            error: "",
            typeLoan: "Select Loan type",
            amountLoan: "",
            paymentLoan: 0,
            targetAccount: "",
            optionPayments: "",
            selectedLoan : {},
            interest: [],
            valuePayment: "",
            interes: "",
            moneyFormat: new Intl.NumberFormat('en-US', { style: 'currency', currency: 'USD' }),
            percentFormat : new Intl.NumberFormat('en-US', {style: 'percent', maximumFractionDigits:2}),
        }
    },
    created() {
        this.loadData()
        this.loadLoans()
    },

    methods: {
        loadData() {
            axios.get('/api/clients/current').then(response => {
                this.client = response.data
                this.accounts = this.client.accounts
            })
        },
        loadLoans() {
            axios.get('/api/loans').then(response => {
                this.loans = response.data
                let interest = this.loans.map(loan => loan.interest)
                console.log(interest)
            })
        },
        findRequestLoan() {
            this.selectedLoan = this.loans.filter(loan => loan.id == this.id)
            this.optionPayments = this.selectedLoan[0].payments
            this.interes = this.selectedLoan[0].interest
            console.log(this.interes)
        },
        alertRequestLoan(){
            let swalWithBootstrapButtons = Swal.mixin({
                customClass: {
                    confirmButton: 'btn btn-success',
                    cancelButton: 'btn btn-danger'
                },
                buttonsStyling: false
            })

            swalWithBootstrapButtons.fire({
                title: '¿Are you sure?',
                text: "Once the requested amount has been credited, you cannot cancel the loan...",
                icon: 'warning',
                showCancelButton: true,
                confirmButtonText: '¡Confirm!',
                cancelButtonText: '¡Cancel!',
           
                reverseButtons: true
            }).then((result) => {
                if (result.isConfirmed) {
                    this.requestLoan()
                    swalWithBootstrapButtons.fire({
                        title: '¡approved loan!',
                        text: 'In moments we will credit the requested amount to the selected account',
                        icon: 'success',
              
                        showConfirmButton: false
                    })
                } else if (
                    /* Read more about handling dismissals below */
                    result.dismiss === Swal.DismissReason.cancel
                ) {
                    swalWithBootstrapButtons.fire({
                        title: '¡cancelled!',
                        icon: 'error',
               
                        showConfirmButton: false
                    })

                }
            })
        },
        requestLoan() {
            axios.post("/api/loans", { id: this.id, amount: this.amount, payments: this.payments, destinationNumberAccount: this.accountNumber })
                .then(response => {

                    setTimeout(() => {
                        window.location.href = "/web/accounts.html"
                    }, 1500)

                }).catch(response => {
                    swal.fire({
                        title: "¡Something was wrong!",
                        text: "An error has occurred, please try again later...",
                        icon: "error",
                        button: "Ok",
                        background: 'var(--table-transactions)'
                    });
                })

        },
        logOut() {
            axios.post('/api/logout').then(response =>
                window.location.href = "/public/index.html"
            )
        },
        paymentValue() {
            this.valuePayment = (this.amount * this.interes + this.amount) / this.payments
            console.log(this.valuePayment)
        },
        },
}).mount('#app')