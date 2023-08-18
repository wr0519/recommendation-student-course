let Config = {
    devServer: {
        proxy: 'http://localhost:8085'
       }       
};

Config.backEndUrl = "http://localhost:8085/api";

export default Config;