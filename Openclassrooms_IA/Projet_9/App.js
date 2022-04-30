import React, {Component} from "react";
import {
    StyleSheet,
    Text,
    View,
    Picker,
    Button,
    Image,
    FlatList
} from "react-native";
import config from "./config.json";
import axios from "axios";

class App extends Component {
    state = {
        selectedUser: 0,
        step: 0,
        recommendations: null
    };


    goToStep0 = async () => {
        this.setState({step: 0, recommendations: null});
    };

    goToStep1 = async () => {



            const {data: recommendations}  = await axios.post(config.API_URL+'?userId='+this.state.selectedUser);
            console.log(recommendations);
            console.log(config.API_URL+'?userId='+this.state.selectedUser );



        this.setState({step: 1, recommendations});
    };


    render() {
        if (!config.API_URL) {
            return (
                <View style={styles.container}>
                    <Image
                        style={{resizeMode: "center", width: 450, height: 150}}
                        source={require("./assets/icon-flat.png")}
                    />
                    <Text style={{color: "red", margin: 20}}>
                        L'app n'est pas configurée correctement. Mettez à jour le fichier
                        config.json comme indiqué dans le README.
                    </Text>
                </View>
            );
        }

        if (this.state.step === 1) {
            return (
                <View style={styles.container}>
                    <Image
                        style={{resizeMode: "center", width: 450, height: 150}}
                        source={require("./assets/icon-flat.png")}
                    />
                    <Text style={{fontSize: 24, padding: 20, textAlign: "center"}}>
                        Vos recommendations
                    </Text>
                    <FlatList
                        style={{maxHeight: 200}}
                        data={this.state.recommendations.map(key => ({
                            key: key.toString()
                        }))}
                        renderItem={({item}) => <Text>Article n°{item.key}</Text>}
                    />
                    <Button title="Se déconnecter" onPress={this.goToStep0}/>
                </View>
            );
        }

        return (
            <View style={styles.container}>
                <Image
                    style={{resizeMode: "center", width: 450, height: 150}}
                    source={require("./assets/icon-flat.png")}
                />
                <Text style={{fontSize: 18, padding: 20, textAlign: "center"}}>
                    Choisissez votre profil afin de recevoir des recommendations de
                    lecture personnalisées
                </Text>
                <Picker
                    style={{height: 200, width: "80%", margin: 30}}
                    selectedValue={this.state.selectedUser}
                    onValueChange={value => this.setState({selectedUser: value})}
                >
                    {[...Array(10000).keys()].map(e => (
                        <Picker.Item key={e} label={`User ${e}`} value={e}/>
                    ))}
                </Picker>
                <Button title="Se connecter" onPress={this.goToStep1}/>
            </View>

        );

    }
}

const styles = StyleSheet.create({
    container: {
        flex: 1,
        backgroundColor: "#fff",
        alignItems: "center",
        justifyContent: "center"
    }
});

export default App;
