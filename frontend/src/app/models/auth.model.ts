export interface Login {
  email: string;
  password: string;
};

export interface Register {
  email: string;
  password: string;
  fullname: string;
  image: string;
};

export interface User {
  id: string;
  email: string;
  fullname: string;
  image: string;
};
