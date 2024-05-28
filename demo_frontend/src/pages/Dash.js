import {useState, useEffect} from 'react';

const Dash = () => {

  let [idToken, setIdToken] = useState('');
  let [fetchCount, setFetchCount] = useState(0);
  let [jwToken, setjwToken] = useState('');


  // When the user is redirected here... get the token 
  useEffect(() => {
    const fetchData = async () => {
      try {
        var fragmentString = window.location.href.split('#')[1];
        let paramsList = fragmentString.split('&');
        let params = {};
        paramsList.forEach(entry => {
          let [key, value] = entry.split('=');
          params[key] = value;
        });
  
        const response = await fetch('http://localhost:8090/oauth2/google', {
          method: 'POST',
          headers: {
            'Authorization': `${params['id_token']}`,
            'Content-Type': 'application/json'
          }
        });
  
        const data = await response.json();
        console.log(data);
        setjwToken(data['jwToken']);
        // Handle the response data as needed
      } catch (error) {
        console.error('Error fetching data:', error);
      }
    };
  
    fetchData();
  }, []);

  useEffect(() => {
    console.log("auth token " + jwToken);
  }, [jwToken])

  const handleClick = () => {
    const fetchData = async () => {
      try {

        const response = await fetch('http://localhost:8090/hello', {
          method: 'GET',
          headers: {
            'Authorization': jwToken,
            'Content-Type': 'application/json'
          }
        });
  
        const data = await response.json();

        // Handle the response data as needed
      } catch (error) {
        console.error('Error fetching data:', error);
      }
    };
  
    fetchData();
  }

  const handleClickNoJwt = () => {
    const fetchData = async () => {
      try {

        const response = await fetch('http://localhost:8090/hello', {
          method: 'GET',
          headers: {
            'Content-Type': 'application/json'
          }
        });
  
        const data = await response.json();

        // Handle the response data as needed
      } catch (error) {
        console.error('Error fetching data:', error);
      }
    };
  
    fetchData();
  }


  return(
    <div>
      <h1>You are Logged In!</h1>
      <button onClick={() => handleClick()}> request with jwt </button>
      <button onClick={() => handleClickNoJwt()}> request without jwt </button>
    </div>
  )
}

export default Dash;