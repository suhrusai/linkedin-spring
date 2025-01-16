import { useState } from "react";
import { useNavigate } from "react-router-dom";
import  Button  from "../../../../components/Button/Button";
import Input  from "../../../../components/Input/Input";
import  Box  from "../../components/Box/Box";
import { useAuthentication } from "../../contexts/AuthenticationContextProvider";
import classes from "./Profile.module.scss";
export default function Profile() {
  const [step, setStep] = useState(0);
  const navigate = useNavigate();
  const { user, setUser } = useAuthentication();
  const [error, setError] = useState("");
  const [data, setData] = useState({
    firstName: "",
    lastName: "",
    company: "",
    position: "",
    location: "",
  });
  const onSubmit = async () => {
    if (!data.firstName || !data.lastName) {
      setError("Please fill in your first and last name.");
      return;
    }
    if (!data.company || !data.position) {
      setError("Please fill in your latest company and position.");
      return;
    }
    if (!data.location) {
      setError("Please fill in your location.");
      return;
    }
    try {
      const res = await fetch(
        `${import.meta.env.VITE_API_URL}/api/v1/authentication/profile/${user?.id}?firstName=${
          data.firstName
        }&lastName=${data.lastName}&company=${data.company}&position=${data.position}&location=${
          data.location
        }`,
        {
          method: "PUT",
          headers: {
            Authorization: `Bearer ${localStorage.getItem("token")}`,
          },
        }
      );
      if (res.ok) {
        const updatedUser = await res.json();
        setUser(updatedUser);
      } else {
        const { message } = await res.json();
        throw new Error(message);
      }
    } catch (error) {
      if (error instanceof Error) {
        setError(error.message);
      } else {
        setError("An unknown error occurred.");
      }
    } finally {
      navigate("/");
    }
  };
  return (
    <div className={classes.root}>
      <Box>
        <h1>Only one last step</h1>
        <p>Tell us a bit about yourself so we can personalize your experience.</p>
        {step === 0 && (
          <div className={classes.inputs}>
            <Input
              onFocus={() => setError("")}
              required
              label="First Name"
              name="firstName"
              placeholder="Jhon"
              onChange={(e) => setData((prev) => ({ ...prev, firstName: e.target.value }))}
            ></Input>
            <Input
              onFocus={() => setError("")}
              required
              label="Last Name"
              name="lastName"
              placeholder="Doe"
              onChange={(e) => setData((prev) => ({ ...prev, lastName: e.target.value }))}
            ></Input>
          </div>
        )}
        {step === 1 && (
          <div className={classes.inputs}>
            <Input
              onFocus={() => setError("")}
              label="Latest company"
              name="company"
              placeholder="Docker Inc"
              onChange={(e) => setData((prev) => ({ ...prev, company: e.target.value }))}
            ></Input>
            <Input
              onFocus={() => setError("")}
              onChange={(e) => setData((prev) => ({ ...prev, position: e.target.value }))}
              label="Latest position"
              name="position"
              placeholder="Software Engineer"
            ></Input>
          </div>
        )}
        {step == 2 && (
          <Input
            onFocus={() => setError("")}
            label="Location"
            name="location"
            placeholder="San Francisco, CA"
            onChange={(e) => setData((prev) => ({ ...prev, location: e.target.value }))}
          ></Input>
        )}
        {error && <p className={classes.error}>{error}</p>}
        <div className={classes.buttons}>
          {step > 0 && (
            <Button outline onClick={() => setStep((prev) => prev - 1)}>
              Back
            </Button>
          )}
          {step < 2 && (
            <Button
              disabled={
                (step === 0 && (!data.firstName || !data.lastName)) ||
                (step === 1 && (!data.company || !data.position))
              }
              onClick={() => setStep((prev) => prev + 1)}
            >
              Next
            </Button>
          )}
          {step === 2 && (
            <Button disabled={!data.location} onClick={onSubmit}>
              Submit
            </Button>
          )}
        </div>
      </Box>
    </div>
  );
}