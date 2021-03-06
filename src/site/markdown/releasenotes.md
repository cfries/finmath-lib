finmath lib Release Notes
==========

****************************************

# Release Notes

## 3.0.0 (27.05.2017)

### Java 6 version of finmath-lib switched from joda-time to threeten-backport

- The Java 6 version of finmath-lib switches from jado-time to threeten-backport for the implementaton of LocalDate. This reduces the difference between the Java 6 code base and the Java 8 code base.
- The Java 8 version is not affected.
- The 2.4.x version using the joda-time version of LocalDate is maintained on the branch named "2.4.x"

### Introducing a dedicated class for the conversion between LocalDate and Double.

- Finmath lib allows to use Double to specify dates/times and date/time-intervals. The motivation of this is to allow a simplified "textbook-like" definition of models, which is often sufficient for research purposes.. With respect to industry/practical applications it is often necessary to consider exact date-based measurment of time, e.g. using date-rolling and daycount conventions. To achive a clear 1:1 mapping, the library fixes a conversion given by a) a reference date and b) a daycount convention (ACT/365).
- The conversion from LocalDate to Double is centralized in the class FloatingpointDate.
- The motivation for ACT/365 is performance (easy conversion). Apart from this there is no specific reason except the need to make some choice.

## 2.4.4 (20.05.2017)

### Introducing a dedicated class for the conversion between LocalDate and Double.

- Finmath lib allows to use Double to specify dates/times and date/time-intervals. The motivation of this is to allow a simplified "textbook-like" definition of models, which is often sufficient for research purposes.. With respect to industry/practical applications it is often necessary to consider exact date-based measurment of time, e.g. using date-rolling and daycount conventions. To achive a clear 1:1 mapping, the library fixes a conversion given by a) a reference date and b) a daycount convention (ACT/365).
- The conversion from LocalDate to Double is centralized in the class FloatingpointDate.
- The motivation for ACT/365 is performance (easy conversion). Apart from this there is no specific reason except the need to make some choice.

## 2.4.2 (05.02.2017)

### Bug fixes and improvements

- Invalidating numeraire caches if model is used with a different (new) process.
- Fixed a failure of date lookup in CapletVolatilityParametric (rare).

## 2.4.1

### Monte-Carlo Interest Rate Model: Term Structure Model with Time Dependent Tenor Discretization

- Added sample implementation of a term structure model with time dependent tenor discretization (see http://ssrn.com/abstract=2884699 ).
- Added calibration of term structure model

### Monte-Carlo Interest Rate Model: LIBOR Market Model

- Refactoring: The objects implementing AbstractLIBORCovarianceModelParametric have become immutable. The method setParameter has been removed and replaced by a method getCloneWithModifiedParameter. This allows a performance increase, since in a calibration it is now safe to reuse parts of the model, given that parts of a parameter (e.g. correlation) has not changed.
- Refactoring: The method getLIBOR(double, double, double) performing the interpoation on the tenor structure has been moved from LIBORModelMonteCarloSimualtionInterface to LIBORModelInterface. This allows to have different models implement different interpolation methods (e.g. LMM versus Hull-White). This chance is also motivated by the introduction of LIBOR models with time dependent tenor discretizations.

### Optimizer

- Levenberg-Marquardt algorithm will stop if improvement is smaller than given accurarcy. Previously the solver was running more interations than required.

## 2.4.0

### Market Data / Schedule Generation

- Added (optional) end-of-month schedule generation to the ScheduleGenerator.

Note: This includes a small change in the schedule generation when schedules are
generated as rolling up/down from a 29th, 30th or 31st.

## 2.3.6

### SABR Model

- Added analytic formulas for SABR skew and SABR curvature.

## 2.3.4

### Market Data / Curve Calibration

- Added valuation of Resettable Cross Currency Swap (aka Mark-to-Market Cross Currency Swap).

## 2.3.0

### Dependencies

- Adding jblas 1.2.4 for LinearAlgebra.

Since the implementation of commons-math matrix solver is sometimes noticable slower,
we switched to jblas. Note that jblas is used by default, but you may use
commons-math instead by setting the property net.finmath.functions.LinearAlgebra.isUseApacheCommonsMath
to true.

Note that jblas is currently not an OSGi bundle. I try to work on that.


## 2.2.6

- Cleanup

## 2.2.5

### Random Number Generators

- Addes Poisson distribution for jump-diffusion processes.
- Added class IndependentIncrements allowing to create mixed process increments (Brownian increments and jump process increments). See Merton model for a demo.

### Market Data / Interest Rates Curve / Multi-Curve

- Added Deposit (may be used as calibration product in multi-curve calibration)
- Added FRA (may be used as calibration product in multi-curve calibration)

### Equity / Single Asset Models

- Added Merton model (Monte-Carlo simulation)
- Added Heston model (Monte-Carlo simulation)
- Added hedge simulation based on mean-variance hedging (using American Monte-Carlo / regression).

### Other

- Some demo spreadsheets have been added at finmath-spreadsheets.

## 2.2.2

### General

- Added OSGi MANIFEST file

## 2.2.0

### Dependencies

- Replaced colt-1.2.0 by apache commons-math-3.6.1.

Since the implementation of MersenneTwister in commons-math differs from that in colt, this
change will lead to small changes in Monte-Carlo valuations.
Since the implementation of linear equation solver in commons-math differes from that in colt, this
change will lead to (very) small changes in calibration parameters.

The switch from colt-1.2.0 to commons-math was necessary to obtain OSGi compliant setup.

### Analytic Formulas

- Added analytic conversion from lognormal to normal (ATM) volatility.

## 2.1.1

### Monte-Carlo Simulation of Interest Rate Models

#### Hull-White Model

- Improved implementation and unit testing. Implementation now uses Browninan motion as a factory for RandomVariableInterface objects.

## 2.1.0

### Monte-Carlo Simulation of Interest Rate Models

#### Interfaces

- From the interface `LIBORMarketModelInterface` the narrower interface  `LIBORModelInterface` has been extracted,
leaving the methods related to the covariance model to `LIBORMarketModelInterface` only. The method `getModel()`
of `LIBORModelMonteCarloSimulationInterface` now returns a `LIBORModelInterface` interface only. In case your
code requires access to the covariance model, you have to check for `LIBORMarketModelInterface` (see the code
of `getValue` in `SwaptionAnalyticApproximation` for an example).

#### Hull-White Model

- Added implementation of a Monte-Carlo simulation of the Hull-White model.

#### LIBOR Market Model

- Added LIBOR Market Model (LMM) local volatiltiy model to generate Hull-White model dynamic in an LMM

## 2.0.3

- Some internals may be configured via Java system properties.
- A bug introduce in 2.0.0 in `DayCountConvention_30U_360` in the Java 6 branch has been fixed

## 2.0.2

- The CalibrationItem of CalibratedCurves may now carry a symbol to create a shifted model (for calculation of sensitivities using finite differences).

## 2.0.0

- API Change: The type java.util.Calendar has been replaced by LocalDate:
    - For Java 6 sources (src/main/java6): Replaced Calendar and Date by org.joda.time.LocalDate.
    - For Java 8 sources (src/main/java): Replaced Calendar and Date by java.time.LocalDate, contributed by William Wong.

- API Change: The DateIndex in net.finmath.montecarlo.interestrate.products.indices now returns the month according to java.time.Month, i.e., January = 1, February = 2, etc.

## 1.3.6

### Swaps

-	Additional constructor for Swap (using SwapLegs).

### Monte Carlo

-	BrownianMotion allows to use a custom AbstractRandomVariableFactory. Useful to switch to single precision floating point numbers (to save memory).
-	ProcessEulerScheme has an addition constructor (to directly construct a predictor corrector scheme).
-	Swaption is now compatible multi-curve LMM (using collateral curve).

### Exposure

-	Exposure has been renamed to ExposureEstimator (since it is just an estimator, see the corresponding unit test for an application).
-	Improved the unit test ExposureTest.

### Volatility

-	Added SABR analytic approximations to net.finmath.functions.AnalyticFormulas
-	BrownianMotion allows to use a custom AbstractRandomVariableFactory. Useful to switch to single precision floating point numbers (to save memory).

### Other

-	Constructor for trapezoidal rule using equi-distant grid.

## 1.3.5

-    Added trapezoidal rule to net.finmath.integration and corresponding unit test.


## 1.3.4 and earlier

Please check the subversion or git log messages.