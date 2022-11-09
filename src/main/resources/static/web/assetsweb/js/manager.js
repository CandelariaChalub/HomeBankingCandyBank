const { createApp } = Vue
createApp({
    data() {
        return {
            clients: [],
            clientName: "",
            clientLastName: "",
            clientEmail: "",
            }},
    created() {
        this.loadData()
    },
methods: {
    loadData(){
        axios.get('/api/clients').then(response =>{
            this.clients = response.data
        })
    },
    addClient(){
        if(this.clientName != "" && this.clientLastName != "" && this.clientEmail != "") {
            this.postClient()
        }
    },
    postClient(){
        axios.post('/rest/clients', {
            name: this.clientName,
            lastName: this.clientLastName,
            email: this.clientEmail
        }).then(response => {
            this.clients.push(response.data)
            this.clientEmail = ""
            this.clientLastName = ""
            this.clientName = ""
        })
    },
    deleteClient(id, event){
        axios.delete("/api/clients/" + id)
        .then(()=> this.loadData())                 
        .catch(error => console.log(error.message))
    }
    }
},
).mount('#app')