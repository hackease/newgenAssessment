As a Newgen employee, you have the flexibility to work from any of the company's offices. As a Newgen employee, you have the flexibility to work from any of the company's offices located in different cities. Each office observes its own set of holidays based on its respective city. Consequently, the number of holidays in a specific month can vary from one office to another, depending on the city it is situated in.

Newgen has implemented a policy that allows employees to change their designated office location on a monthly basis if desired.

However, there are conditions to this flexibility:
1. The new office must be within a 100-kilometer radius of the current office.
2. You are allowed to change your office a maximum of twice in any quarter (3-month period). 
3. The total number of relocations cannot exceed 8 for the year. 

Your goal as an employee is to maximize the number of regional holidays you can enjoy throughout the year. To achieve this, you need to develop an algorithm that efficiently identifies the office changes you should make to optimize your holiday count, while adhering to the relocation constraints. 

You will be provided with:
1. A map that indicates the offices located within a 100-kilometer radius of each other. 
2. A list of holidays for each office on a month-by-month basis for a given year. 

Additional Constraints: 
1. You can choose any office as your starting point. 
2. You can only change your office twice within a 3-month quarter (quarters are Jan-Mar, Apr-Jun, Jul-Sep, Oct-Dec). 
3. The maximum number of reallocation in a year is 8. 

Input: Offices within a 100km range of base office (as a dictionary of lists) json: 

{
    "Noida": ["Delhi", "Gurugram","Faridabad"],
    "Delhi": ["Noida", "Sonipat", "Gurugram", "Faridabad"], 
    "Sonipat": ["Delhi", "Gurugram", "Panipat"], 
    "Gurugram": ["Noida", "Delhi", "Sonipat", "Panipat", "Faridabad"], 
    "Panipat": ["Sonipat", "Gurugram"], 
    "Faridabad": ["Noida", "Delhi", "Gurugram"] 
}

Month-wise holidays for each office (as a dictionary of lists) json: 

{
    "Noida": [1, 3, 4, 2, 1, 5, 6, 5, 1, 7, 2, 1], 
    "Delhi": [5, 1, 8, 2, 1, 7, 2, 6, 2, 8, 2, 6], 
    "Sonipat": [2, 5, 8, 2, 1, 6, 9, 3, 2, 1, 5, 7], 
    "Gurugram": [6, 4, 1, 6, 3, 4, 7, 3, 2, 5, 7, 8], 
    "Panipat": [2, 4, 3, 1, 7, 2, 6, 8, 2, 1, 4, 6], 
    "Faridabad": [2, 4, 6, 7, 2, 1, 3, 6, 3, 1, 6, 8] 
}

Objective: Write an algorithm to maximize the number of holidays you can enjoy over the year, while respecting the relocation limits and the constraint of only two moves within any given quarter.

Expected Output: A sequence of office transitions for each month that maximizes your holiday count. The total number of holidays enjoyed based on this strategy.