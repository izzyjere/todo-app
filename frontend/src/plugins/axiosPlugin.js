import axios from 'axios';

export default function axiosPlugin(store) {  
  store.subscribe((mutation) => {
    if (mutation.type === 'SET_TOKEN') {
      const token = mutation.payload;
      axios.defaults.headers.common['Authorization'] = token ? `Bearer ${token}` : null;
    }
  });
}
