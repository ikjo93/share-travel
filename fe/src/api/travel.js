import { travelKeywords, travels } from '@/api/index';

function getTravelKeywords() {
  return travelKeywords.get();
}

function registerTravel(data) {
  const formData = new FormData();

  formData.append('name', data.name);
  formData.append('description', data.description);
  formData.append('travelKeywordId', data.travelKeywordId);
  Array.from(data.files).forEach(file => {
    formData.append('files', file);
  });

  formData.append('longitude', data.longitude);
  formData.append('latitude', data.latitude);

  return travels.post('', formData, {
    headers: {
      'Content-Type': 'multipart/form-data',
    },
  });
}

export { getTravelKeywords, registerTravel };
